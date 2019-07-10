package sales.salesmen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class WxUser {

    @Id
    @Column(nullable = false,length = 40,unique = true)
    String wxopenid;


    @Column(nullable = false,unique = true)
    Long userid;
}
