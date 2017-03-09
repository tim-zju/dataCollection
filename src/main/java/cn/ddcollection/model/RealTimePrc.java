package cn.ddcollection.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RealTimePrc {
    int id;
    String Symbol;
	String Name;
	BigDecimal TodayStartPrc;
	BigDecimal YesPrc;
	BigDecimal CurrentPrc;
	BigDecimal HighPrc;
	BigDecimal LowPrc;
	BigDecimal BuyPrc;
	BigDecimal SelPrc;
	int FinishVol;
	BigDecimal FinishPrc;
	BigDecimal BuyOnePrc;
	BigDecimal BuyTwoPrc;
	BigDecimal BuyThreePrc;
	BigDecimal BuyFourPrc;
	BigDecimal BuyFivePrc;
	BigDecimal SelOnePrc;
	BigDecimal SelTwoPrc;
	BigDecimal SelThreePrc;
	BigDecimal SelFourPrc;
	BigDecimal SelFivePrc;
	int BuyOneAmt;
	int BuyTwoAmt;
	int BuyThreeAmt;
	int BuyFourAmt;
	int BuyFiveAmt;
	int SelOneAmt;
	int SelTwoAmt;
	int SelThreeAmt;
	int SelFourAmt;
	int SelFiveAmt;
	Timestamp Time;
	
	public RealTimePrc(){}
	
	public RealTimePrc(String symbol, String name, BigDecimal todayStartPrc,
			BigDecimal yesPrc, BigDecimal currentPrc, BigDecimal highPrc,
			BigDecimal lowPrc, BigDecimal buyPrc, BigDecimal selPrc,
			int finishVol, BigDecimal finishPrc, BigDecimal buyOnePrc,
			BigDecimal buyTwoPrc, BigDecimal buyThreePrc,
			BigDecimal buyFourPrc, BigDecimal buyFivePrc, BigDecimal selOnePrc,
			BigDecimal selTwoPrc, BigDecimal selThreePrc,
			BigDecimal selFourPrc, BigDecimal selFivePrc, int buyOneAmt,
			int buyTwoAmt, int buyThreeAmt, int buyFourAmt, int buyFiveAmt,
			int selOneAmt, int selTwoAmt, int selThreeAmt, int selFourAmt,
			int selFiveAmt, Timestamp time) {
		super();
		Symbol = symbol;
		Name = name;
		TodayStartPrc = todayStartPrc;
		YesPrc = yesPrc;
		CurrentPrc = currentPrc;
		HighPrc = highPrc;
		LowPrc = lowPrc;
		BuyPrc = buyPrc;
		SelPrc = selPrc;
		FinishVol = finishVol;
		FinishPrc = finishPrc;
		BuyOnePrc = buyOnePrc;
		BuyTwoPrc = buyTwoPrc;
		BuyThreePrc = buyThreePrc;
		BuyFourPrc = buyFourPrc;
		BuyFivePrc = buyFivePrc;
		SelOnePrc = selOnePrc;
		SelTwoPrc = selTwoPrc;
		SelThreePrc = selThreePrc;
		SelFourPrc = selFourPrc;
		SelFivePrc = selFivePrc;
		BuyOneAmt = buyOneAmt;
		BuyTwoAmt = buyTwoAmt;
		BuyThreeAmt = buyThreeAmt;
		BuyFourAmt = buyFourAmt;
		BuyFiveAmt = buyFiveAmt;
		SelOneAmt = selOneAmt;
		SelTwoAmt = selTwoAmt;
		SelThreeAmt = selThreeAmt;
		SelFourAmt = selFourAmt;
		SelFiveAmt = selFiveAmt;
		Time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public BigDecimal getTodayStartPrc() {
		return TodayStartPrc;
	}

	public void setTodayStartPrc(BigDecimal todayStartPrc) {
		TodayStartPrc = todayStartPrc;
	}

	public BigDecimal getYesPrc() {
		return YesPrc;
	}

	public void setYesPrc(BigDecimal yesPrc) {
		YesPrc = yesPrc;
	}

	public BigDecimal getCurrentPrc() {
		return CurrentPrc;
	}

	public void setCurrentPrc(BigDecimal currentPrc) {
		CurrentPrc = currentPrc;
	}

	public BigDecimal getHighPrc() {
		return HighPrc;
	}

	public void setHighPrc(BigDecimal highPrc) {
		HighPrc = highPrc;
	}

	public BigDecimal getLowPrc() {
		return LowPrc;
	}

	public void setLowPrc(BigDecimal lowPrc) {
		LowPrc = lowPrc;
	}

	public BigDecimal getBuyPrc() {
		return BuyPrc;
	}

	public void setBuyPrc(BigDecimal buyPrc) {
		BuyPrc = buyPrc;
	}

	public BigDecimal getSelPrc() {
		return SelPrc;
	}

	public void setSelPrc(BigDecimal selPrc) {
		SelPrc = selPrc;
	}

	public int getFinishVol() {
		return FinishVol;
	}

	public void setFinishVol(int finishVol) {
		FinishVol = finishVol;
	}

	public BigDecimal getFinishPrc() {
		return FinishPrc;
	}

	public void setFinishPrc(BigDecimal finishPrc) {
		FinishPrc = finishPrc;
	}

	public BigDecimal getBuyOnePrc() {
		return BuyOnePrc;
	}

	public void setBuyOnePrc(BigDecimal buyOnePrc) {
		BuyOnePrc = buyOnePrc;
	}

	public BigDecimal getBuyTwoPrc() {
		return BuyTwoPrc;
	}

	public void setBuyTwoPrc(BigDecimal buyTwoPrc) {
		BuyTwoPrc = buyTwoPrc;
	}

	public BigDecimal getBuyThreePrc() {
		return BuyThreePrc;
	}

	public void setBuyThreePrc(BigDecimal buyThreePrc) {
		BuyThreePrc = buyThreePrc;
	}

	public BigDecimal getBuyFourPrc() {
		return BuyFourPrc;
	}

	public void setBuyFourPrc(BigDecimal buyFourPrc) {
		BuyFourPrc = buyFourPrc;
	}

	public BigDecimal getBuyFivePrc() {
		return BuyFivePrc;
	}

	public void setBuyFivePrc(BigDecimal buyFivePrc) {
		BuyFivePrc = buyFivePrc;
	}

	public BigDecimal getSelOnePrc() {
		return SelOnePrc;
	}

	public void setSelOnePrc(BigDecimal selOnePrc) {
		SelOnePrc = selOnePrc;
	}

	public BigDecimal getSelTwoPrc() {
		return SelTwoPrc;
	}

	public void setSelTwoPrc(BigDecimal selTwoPrc) {
		SelTwoPrc = selTwoPrc;
	}

	public BigDecimal getSelThreePrc() {
		return SelThreePrc;
	}

	public void setSelThreePrc(BigDecimal selThreePrc) {
		SelThreePrc = selThreePrc;
	}

	public BigDecimal getSelFourPrc() {
		return SelFourPrc;
	}

	public void setSelFourPrc(BigDecimal selFourPrc) {
		SelFourPrc = selFourPrc;
	}

	public BigDecimal getSelFivePrc() {
		return SelFivePrc;
	}

	public void setSelFivePrc(BigDecimal selFivePrc) {
		SelFivePrc = selFivePrc;
	}

	public int getBuyOneAmt() {
		return BuyOneAmt;
	}

	public void setBuyOneAmt(int buyOneAmt) {
		BuyOneAmt = buyOneAmt;
	}

	public int getBuyTwoAmt() {
		return BuyTwoAmt;
	}

	public void setBuyTwoAmt(int buyTwoAmt) {
		BuyTwoAmt = buyTwoAmt;
	}

	public int getBuyThreeAmt() {
		return BuyThreeAmt;
	}

	public void setBuyThreeAmt(int buyThreeAmt) {
		BuyThreeAmt = buyThreeAmt;
	}

	public int getBuyFourAmt() {
		return BuyFourAmt;
	}

	public void setBuyFourAmt(int buyFourAmt) {
		BuyFourAmt = buyFourAmt;
	}

	public int getBuyFiveAmt() {
		return BuyFiveAmt;
	}

	public void setBuyFiveAmt(int buyFiveAmt) {
		BuyFiveAmt = buyFiveAmt;
	}

	public int getSelOneAmt() {
		return SelOneAmt;
	}

	public void setSelOneAmt(int selOneAmt) {
		SelOneAmt = selOneAmt;
	}

	public int getSelTwoAmt() {
		return SelTwoAmt;
	}

	public void setSelTwoAmt(int selTwoAmt) {
		SelTwoAmt = selTwoAmt;
	}

	public int getSelThreeAmt() {
		return SelThreeAmt;
	}

	public void setSelThreeAmt(int selThreeAmt) {
		SelThreeAmt = selThreeAmt;
	}

	public int getSelFourAmt() {
		return SelFourAmt;
	}

	public void setSelFourAmt(int selFourAmt) {
		SelFourAmt = selFourAmt;
	}

	public int getSelFiveAmt() {
		return SelFiveAmt;
	}

	public void setSelFiveAmt(int selFiveAmt) {
		SelFiveAmt = selFiveAmt;
	}

	public Timestamp getTime() {
		return Time;
	}

	public void setTime(Timestamp time) {
		Time = time;
	}

	


}
