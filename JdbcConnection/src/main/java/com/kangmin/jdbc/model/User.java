package com.kangmin.jdbc.model;

public class User {
    private int uid;
    private String userName;  // unique email address
    private String password;
    private String firstName;
    private String lastName;

    public int getUid()			{ return uid;       }
    public String getPassword()        	{ return password;  }
    public String getUserName()        	{ return userName;  }
    public String getFirstName()       	{ return firstName; }
    public String getLastName()        	{ return lastName;  }

    // public void setUid(String s)       { password = s;  }
    public void setPassword(String s)  { password = s;  }
    public void setUserName(String s)  { userName = s;  }
    public void setFirstName(String s) { firstName = s; }
    public void setLastName(String s)  { lastName = s;  }
}
