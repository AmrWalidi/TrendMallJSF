package entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SiparisUrunId implements Serializable {

    private int siparis;
    private int urun;

    public SiparisUrunId(int siparis, int urun) {
        this.siparis = siparis;
        this.urun = urun;
    }

    public int getSiparis() {
        return siparis;
    }

    public void setSiparis(int siparis) {
        this.siparis = siparis;
    }

    public int getUrun() {
        return urun;
    }

    public void setUrun(int urun) {
        this.urun = urun;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.siparis;
        hash = 97 * hash + this.urun;
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
        final SiparisUrunId other = (SiparisUrunId) obj;
        if (this.siparis != other.siparis) {
            return false;
        }
        return this.urun == other.urun;
    }

}
