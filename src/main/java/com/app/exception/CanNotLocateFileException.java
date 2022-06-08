package com.app.exception;

import java.io.FileNotFoundException;

public class CanNotLocateFileException extends FileNotFoundException{
    public CanNotLocateFileException(String errorMessage) {
        super(errorMessage);
    }
}
