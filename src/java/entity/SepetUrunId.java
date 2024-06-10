package entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SepetUrunId implements Serializable{
    
    private int sepet;

    private int urun;

    public SepetUrunId(int sepet, int urun) {
        this.sepet = sepet;
        this.urun = urun;
    }
    

    public int getSepet() {
        return sepet;
    }

    public void setSepet(int sepet) {
        this.sepet = sepet;
    }

    public int getUrun() {
        return urun;
    }

    public void setUrun(int urun) {
        this.urun = urun;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.sepet;
        hash = 29 * hash + this.urun;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SepetUrunId other = (SepetUrunId) obj;
        if (this.sepet != other.sepet) {
            return false;
        }
        return this.urun == other.urun;
    }
    
    
}
