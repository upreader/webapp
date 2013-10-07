package com.crocodoc;

public class UprCrocodocSession{
    public static String create(String uuid) throws CrocodocException {
      return CrocodocSession.create(uuid);
    }
}
