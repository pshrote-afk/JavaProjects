package com.thinking.machines.hr.bl.exceptions;
import java.util.*; //for Map and List collections
public class BLException extends Exception //for Checked Exception
{
private String genericException;
private Map<String,String> exceptions;
public BLException()
{
genericException=null;
exceptions=new HashMap<>();
}
public void setGenericException(String genericException)
{
this.genericException=genericException;
}
public String getGenericException()
{
if(genericException==null) return "";
return this.genericException;
}
public boolean hasGenericException() //check
{
return this.genericException!=null;
}
public List<String> getProperties() //check
{
List<String> properties = new ArrayList<>();
this.exceptions.forEach((key,value)->{
properties.add(key);
});
return properties;
}

public void addException(String property,String exception)
{
this.exceptions.put(property,exception);
}
public String getException(String property) //check
{
return this.exceptions.get(property);
}
public void removeException(String property)
{
this.exceptions.remove(property);
}
public boolean hasException(String property)
{
return this.exceptions.containsKey(property);
}
public int getExceptionsCount()
{
if(genericException==null) return this.exceptions.size();
return this.exceptions.size()+1;
}
public boolean hasExceptions()
{
return this.getExceptionsCount()>0;
}
public String getMessage()
{
if(this.genericException==null) return "";
return this.genericException;
}
}