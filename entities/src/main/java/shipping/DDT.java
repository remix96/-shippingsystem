package shipping;

import javax.persistence.*;

@Entity
public class DDT {

    @Id
    private Integer id;
    private Integer seq;

    public Integer getId() {
        return id;
    }

    public DDT setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSeq() {
        return seq;
    }

    public DDT setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }

    @Override
    public String toString() {
        return "DDT{" +
                "id=" + id +
                ", seq=" + seq +
                '}';
    }

}
