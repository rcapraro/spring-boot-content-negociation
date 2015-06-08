package demo;

import com.google.common.io.ByteStreams;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DemoController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Doc[] foos() {
        Doc[] docses = new Doc[2];
        docses[0] = new Doc("foo", "bar");
        docses[1] = new Doc("john", "doe");
        return docses;
    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> getDocs(@RequestParam("error") boolean error) throws IOException {
        byte[] doc;
        HttpHeaders headers = new HttpHeaders();
        try {
            ClassPathResource myPdf = new ClassPathResource("saint-petersburg-1-0.pdf");
            headers.set("Content-Disposition", "attachment; filename=saint-petersburg-1-0.pdf");
            headers.setContentLength(myPdf.getFile().length());

            doc = ByteStreams.toByteArray(myPdf.getInputStream());

            if (error) {
                throw new NullPointerException();
            }

            return new ResponseEntity<>(doc, headers, HttpStatus.OK);

        } catch (Exception e) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            throw new IllegalArgumentException("PDF NOT FOUND");
        }
    }

}
