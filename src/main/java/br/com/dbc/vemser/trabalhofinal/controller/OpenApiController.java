package br.com.dbc.vemser.trabalhofinal.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Hidden
@RestController
public class OpenApiController {

//    @Operation(hidden = true)
//    @GetMapping(value = "/")
//    public void index(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/swagger-ui/index.html");
//    }

}