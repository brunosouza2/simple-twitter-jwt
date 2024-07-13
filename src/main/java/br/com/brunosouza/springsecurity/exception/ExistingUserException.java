package br.com.brunosouza.springsecurity.exception;

public class ExistingUserException extends IllegalArgumentException {

    public ExistingUserException(String s) {
        super(s);
    }
}
