package com.example.wsr_iadrikhinskii;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ValCurs {
    @Attribute(name = "Date")
    private String date;

    @Attribute
    private String name;

    @ElementList(inline=true)
    private List<Valute> valute;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Valute> getValutes() {
        return valute;
    }

    public void setValutes(List<Valute> valute) {
        this.valute = valute;
    }
}
