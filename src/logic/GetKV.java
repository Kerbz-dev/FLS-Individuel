package logic;

import java.util.function.Consumer;

import com.ferrari.finances.dk.bank.InterestRate;
import com.ferrari.finances.dk.rki.CreditRator;
import com.ferrari.finances.dk.rki.Rating;

public class GetKV extends Thread {

	public GetKV() {

	}


	public enum kreditRating {
		A, B, C, D, error
	};

	LaanCheckTlf checkTlfNr = new LaanCheckTlf();
	// private double udl�nsrente;
	private double rente, bilpris, kundeindbetaling, samletpris, mdlYdelse, laanlaengde;

	private Rating kv;

	public kreditRating getKreditvaerdighed(String cpr) {
		kv = CreditRator.i().rate(cpr);
		

		
		if (kv == Rating.A) {
			
			return kreditRating.A;
		} else if (kv == Rating.B) {
			return kreditRating.B;
		} else if (kv == Rating.C) {
			return kreditRating.C;

		} else if (kv == Rating.D) {
			return kreditRating.D;
		}

		else {			
			return kreditRating.error;
		
		}
		
	
		
	}
	
	public double calcRente(String cprnr, String bilprisGetText, String udbetalingGetText, String laanleangdeGetText) {
		rente = InterestRate.i().todaysRate();

		kv = CreditRator.i().rate(cprnr);
		bilpris = Double.parseDouble(bilprisGetText);
		laanlaengde = Double.parseDouble(laanleangdeGetText);
		kundeindbetaling = Double.parseDouble(udbetalingGetText);

		if (kv == Rating.A) {
			rente += 1;

			if (laanlaengde > 3) {
				rente += 1;
			}
			if (kundeindbetaling < bilpris / 2) {
				rente += 1;
			}
			setRente(rente);
			return rente;
		} else if (kv == Rating.B) {
			rente += 2;

			if (laanlaengde > 3) {
				rente += 1;
			}
			if (kundeindbetaling < bilpris / 2) {
				rente += 1;
			}
			setRente(rente);
			return rente;
		} else if (kv == Rating.C) {
			rente += 3;

			if (laanlaengde > 3) {
				rente += 1;
			}
			if (kundeindbetaling < bilpris / 2) {
				rente += 1;
			}
			setRente(rente);
			return rente;

		} else if (kv == Rating.D) {
			rente = 0;
			setRente(rente);
			return rente;
		}

		else {
			rente = -1;
			setRente(rente);
			return rente;
		}
	}

	public double calcPris(String bilprisGetText, String udbetalingGetText, String laanleangdeGetText) {
		rente = getRente();
		rente = rente / 100 + 1;
		bilpris = Double.parseDouble(bilprisGetText);
		laanlaengde = Double.parseDouble(laanleangdeGetText);
		kundeindbetaling = Double.parseDouble(udbetalingGetText);

		if (rente == 0) {

			return 0;
		}

		else if (rente == -1) {
			return -1;
		}

		samletpris = ((bilpris - kundeindbetaling) * Math.pow(rente, laanlaengde));
		mdlYdelse = (samletpris / (laanlaengde * 12));

		setMdlYdelse(mdlYdelse);
		setSamletpris(samletpris);
		return mdlYdelse;

	}
	
	public kreditRating getKVWithCallback(String cpr, Consumer<kreditRating> callback) {
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				kreditRating kv = getKreditvaerdighed(cpr);
				callback.accept(kv);
			}
		};

		new Thread(runner).start();
		return null;
	}

	public double getRente() {
		return rente;
	}

	public void setRente(double rente) {
		this.rente = rente;
	}

	public double getSamletpris() {
		return samletpris;
	}

	public void setSamletpris(double samletpris) {
		this.samletpris = samletpris;
	}

	public double getMdlYdelse() {
		return mdlYdelse;
	}

	public void setMdlYdelse(double mdlYdelse) {
		this.mdlYdelse = mdlYdelse;
	}

}
