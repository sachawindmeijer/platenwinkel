package com.example.platenwinkel.models;

import java.io.Serializable;
import java.util.Objects;

public class AuthorityKey implements Serializable
{

    private String username;
    private String authority;

    // Dit kan weg (chatgpt) 19-10
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityKey that = (AuthorityKey) o;
        return Objects.equals(username, that.username) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }


}
