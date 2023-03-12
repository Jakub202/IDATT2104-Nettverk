package ntnu.backend.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class CodeService {
    private static final String DOCKER_IMAGE_NAME = "compile-image";
    private static final String DOCKER_CONTAINER_NAME = "compile-container";

    public String compileCode(String code) throws Exception {
        // Run Docker container to compile and run the code

        //String dockerCommand = "docker run --name " + DOCKER_CONTAINER_NAME + " --rm -v " + System.getProperty("user.dir") + "/sourcecode:/app " + DOCKER_IMAGE_NAME + " /bin/bash -c \"echo \'" + code + "\' > /app/Test.java && cd /app && javac Test.java && java Test\"";
        //String dockerCommand2 = "docker run --rm openjdk:latest sh -c 'echo \"$code\" > HelloWorld.java && javac HelloWorld.java && java HelloWorld'\n";


        String[] command = {
                "docker", "run", "--rm", "openjdk:latest", "sh", "-c",
                "echo \"" + code + "\" > HelloWorld.java && javac HelloWorld.java && java HelloWorld"
        };

        String[] command2 = {
                "docker", "run", "--rm", "python:latest", "python", "-c", code
        };
        System.out.println(code);
        Process process = Runtime.getRuntime().exec(command2);
        int exitCode = process.waitFor();
// Wait for the command to finish and get the output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }


        System.out.println("Exit code: " + exitCode);
        System.out.println("Output: " + output.toString());


        return output.toString();
    }
}
