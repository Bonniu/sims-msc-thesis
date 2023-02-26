package mgr.sims.logs.controller;

import lombok.RequiredArgsConstructor;
import mgr.sims.logs.Log;
import mgr.sims.logs.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Log> getLogs() {
        return logService.getLogs();
    }


}
