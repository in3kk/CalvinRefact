package Refact.CalvinRefact.entity.embed;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String address;
    private String address_detail;

    public Address(String address, String address_detail) {
        this.address = address;
        this.address_detail = address_detail;
    }

    public Address() {
    }

    @Override
    public String toString(){
        return this.address+" "+this.address_detail;
    }
}
