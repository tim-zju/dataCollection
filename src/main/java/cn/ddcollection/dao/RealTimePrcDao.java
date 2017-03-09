package cn.ddcollection.dao;

import org.apache.ibatis.annotations.Insert;

import cn.ddcollection.model.RealTimePrc;

public interface RealTimePrcDao {
	@Insert("insert into realtime_prc(Symbol, Name, TodayStartPrc, YesPrc, CurrentPrc, HighPrc,LowPrc,"
			+ " BuyPrc, SelPrc, FinishVol, FinishPrc, BuyOnePrc, BuyTwoPrc, BuyThreePrc, BuyFourPrc, "
			+ "BuyFivePrc, SelOnePrc, SelTwoPrc, SelThreePrc, SelFourPrc, SelFivePrc, BuyOneAmt, BuyTwoAmt,"
			+ "BuyThreeAmt, BuyFourAmt, BuyFiveAmt, SelOneAmt, SelTwoAmt, SelThreeAmt, "
			+ "SelFourAmt, SelFiveAmt, Time) values(#{Symbol},#{Name},#{TodayStartPrc},#{YesPrc},#{CurrentPrc},"
			+ "#{HighPrc},#{LowPrc},#{BuyPrc},#{SelPrc},#{FinishVol},#{FinishPrc},#{BuyOnePrc},#{BuyTwoPrc},"
			+ "#{BuyThreePrc},#{BuyFourPrc},#{BuyFivePrc},"
			+ "#{SelOnePrc},#{SelTwoPrc},#{SelThreePrc},#{SelFourPrc},#{SelFivePrc},#{BuyOneAmt},#{BuyTwoAmt},"
			+ "#{BuyThreeAmt},#{BuyFourAmt},#{BuyFiveAmt},"
			+ "#{SelOneAmt},#{SelTwoAmt},#{SelThreeAmt},#{SelFourAmt},#{SelFiveAmt},#{Time})")
	public int insertRealTimePrc(RealTimePrc realTimePrc);
}
