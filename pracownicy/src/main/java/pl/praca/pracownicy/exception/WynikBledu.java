package pl.praca.pracownicy.exception;

import java.util.Date;

public class WynikBledu {

    private Date timestamp;
    private String wiadomosc;
    private String opis;

    public WynikBledu(Date timestamp, String wiadomosc, String opis)
    {
        super();
        this.timestamp = timestamp;
        this.wiadomosc = wiadomosc;
        this.opis = opis;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public String getWiadomosc()
    {
        return wiadomosc;
    }

    public String getOpis()
    {
        return opis;
    }
}
