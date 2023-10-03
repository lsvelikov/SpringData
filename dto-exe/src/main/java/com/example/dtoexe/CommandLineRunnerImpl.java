package com.example.dtoexe;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements org.springframework.boot.CommandLineRunner {

    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Please enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]) {

            }
        }
    }
}
