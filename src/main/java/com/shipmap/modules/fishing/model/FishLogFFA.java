package com.shipmap.modules.fishing.model;

/**
 * @author qinghua
 * @since 2019-2-15
 * FFA报表
 */
public class FishLogFFA {

    private Integer id;

    private Integer pageno;
    private Integer pagesize;

    private String month; //月
    private String dat; //日
    private String activity;//活动代码
    private String lon;//经度
    private String lat;//纬度
    private String hooklon;//下钓纬度
    private String hooklat;//下钓经度
    private String hooktime;//下钓时间
    private Integer hooknum;//下钓数量
    private Integer hookfloat;//浮子间钓钩数量
    private String ss;

    private String shipName; //船名
    private String compName;//公司名称
    private String FFANumber;//FFA注册号
    private String shipRegistration;//船旗国
    private String registrationNumber;//国籍证书号码
    private String identification; //WCPFC认证号
    private String Integerernational;//无线电
    private String fishNumber;//入渔许可
    private String unLoading;//卸货目的地
    private String agent;//转载代理商
    private String species;//主捕鱼种
    private String departure;//出发港口
    private String placeUnload;//卸鱼位置
    private String year;//年
    private String departuretime;//出港日期
    private String trip;//第几次航行
    private String pcTime;//进港日期
    private String exporttime; //导出时间


    private Integer albacore;//ALBACORE（长鳍）
    private Integer yellowfin;//YELLOWFIN（黄鳍）
    private Integer yellowfin2;//SKIPJACK（鲣鱼）
    private Integer yellowfin3;//YELLOWFIN（小黄）
    private Integer bigeye; //BIGEYE（大目）
    private Integer bigeye2; //BLACK MARLIN（印度枪鱼））
    private Integer bigeye3; //BLUE  MARLIN（蓝枪鱼）
    private Integer bigeye4; //STRIPED MARLIN（条纹四鳍旗鱼）
    private Integer striped;//STRIPED MARLIN（红旗）
    private Integer swordfish;//SWORDFISH（箭鱼）
    private Integer oilfish;//OILFISH(油甘)
    private Integer mahimahi;//MAHIMAHI(鬼头刀)
    private Integer bluemarlin;//SAILFISH（旗鱼）
    private Integer wahoo;//WAHOO(马交)
    private Integer skipjack;//SKIPJACK（炸弹鱼）
    private Integer blackmarlin;//BLACK MARLIN（枪鱼）
    private Integer sailfish;//SAILFISH（伞旗）
    private Integer moonfish;//MOONFISH(红皮刀)
    private Integer blackpomfret;//BLACK POMFRET(黑皮刀)
    private Integer sevefish;//（7下大目黄鳍）
    private Integer shark;//BLUE SHARK（大青鲨）
    private Integer bycatch;//BY-CATCH(杂鱼)
    private Integer blueshark;//BLUE SHARK（大青鲨）
    private Integer hammerhead;//HAMMERHEAD SHK（双髻鲨）
    private Integer makosharks;//MAKO   SHARKS（灰鲭鲨）
    private Integer oceanic;//OCEANIC WHITETIP（长鳍真鲨）
    private Integer silkyshark;//SILKY SHARK（镰鳍真鲨）
    private Integer threshersharks;//THRESHER SHARKS（长尾鲨）
    private Integer pacific;//PACIFIC BLUEFIN（太平洋蓝鳍）


    private Double albacore1;//ALBACORE（长鳍）
    private Double yellowfin1;//YELLOWFIN（黄鳍）
    private Double yellowfin21;//YELLOWFIN（中黄）
    private Double yellowfin31;//YELLOWFIN（小黄）
    private Double bigeye1; //BIGEYE（大目）
    private Double bigeye21; //BIGEYE（中目）
    private Double bigeye31; //BIGEYE（小目）
    private Double bigeye41; //BIGEYE（小小目）
    private Double striped1;//STRIPED MARLIN（红旗）
    private Double swordfish1;//SWORDFISH（箭鱼）
    private Double oilfish1;//OILFISH(油甘)
    private Double mahimahi1;//MAHIMAHI(鬼头刀)
    private Double bluemarlin1;//BLUE  MARLIN（黑旗）
    private Double wahoo1;//WAHOO(马交)
    private Double skipjack1;//SKIPJACK（炸弹鱼）
    private Double blackmarlin1;//BLACK MARLIN（枪鱼）
    private Double sailfish1;//SAILFISH（伞旗）
    private Double moonfish1;//MOONFISH(红皮刀)
    private Double blackpomfret1;//BLACK POMFRET(黑皮刀)
    private Double sevefish1;//（7下大目黄鳍）
    private Double shark1;//SHARK（鲨鱼）
    private Double bycatch1;//BY-CATCH(杂鱼)
    private Double blueshark1;//BLUE SHARK（大青鲨）
    private Double hammerhead1;//HAMMERHEAD SHK（双髻鲨）
    private Double makosharks1;//MAKO   SHARKS（灰鲭鲨）
    private Double oceanic1;//OCEANIC WHITETIP（长鳍真鲨）
    private Double silkyshark1;//SILKY SHARK（镰鳍真鲨）
    private Double threshersharks1;//THRESHER SHARKS（长尾鲨）
    private Double pacific1;//PACIFIC BLUEFIN（太平洋蓝鳍）

    private Integer albacore2;//ALBACORE（长鳍）
    private Integer yellowfin222;//YELLOWFIN（黄鳍）
    private Integer yellowfin22;//YELLOWFIN（中黄）
    private Integer yellowfin32;//YELLOWFIN（小黄）
    private Integer bigeye222; //BIGEYE（大目）
    private Integer bigeye22; //BIGEYE（中目）
    private Integer bigeye32; //BIGEYE（小目）
    private Integer bigeye42; //BIGEYE（小小目）
    private Integer striped2;//STRIPED MARLIN（红旗）
    private Integer swordfish2;//SWORDFISH（箭鱼）
    private Integer oilfish2;//OILFISH(油甘)
    private Integer mahimahi2;//MAHIMAHI(鬼头刀)
    private Integer bluemarlin2;//BLUE  MARLIN（黑旗）
    private Integer wahoo2;//WAHOO(马交)
    private Integer skipjack2;//SKIPJACK（炸弹鱼）
    private Integer blackmarlin2;//BLACK MARLIN（枪鱼）
    private Integer sailfish2;//SAILFISH（伞旗）
    private Integer moonfish2;//MOONFISH(红皮刀)
    private Integer blackpomfret2;//BLACK POMFRET(黑皮刀)
    private Integer sevefish2;//（7下大目黄鳍）
    private Integer shark2;//SHARK（鲨鱼）
    private Integer bycatch2;//BY-CATCH(杂鱼)
    private Integer blueshark2;//BLUE SHARK（大青鲨）
    private Integer hammerhead2;//HAMMERHEAD SHK（双髻鲨）
    private Integer makosharks2;//MAKO   SHARKS（灰鲭鲨）
    private Integer oceanic2;//OCEANIC WHITETIP（长鳍真鲨）
    private Integer silkyshark2;//SILKY SHARK（镰鳍真鲨）
    private Integer threshersharks2;//THRESHER SHARKS（长尾鲨）
    private Integer pacific2;//PACIFIC BLUEFIN（太平洋蓝鳍）


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getHooklon() {
        return hooklon;
    }

    public void setHooklon(String hooklon) {
        this.hooklon = hooklon;
    }

    public String getHooklat() {
        return hooklat;
    }

    public void setHooklat(String hooklat) {
        this.hooklat = hooklat;
    }

    public String getHooktime() {
        return hooktime;
    }

    public void setHooktime(String hooktime) {
        this.hooktime = hooktime;
    }

    public Integer getHooknum() {
        return hooknum;
    }

    public void setHooknum(Integer hooknum) {
        this.hooknum = hooknum;
    }

    public Integer getHookfloat() {
        return hookfloat;
    }

    public void setHookfloat(Integer hookfloat) {
        this.hookfloat = hookfloat;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getFFANumber() {
        return FFANumber;
    }

    public void setFFANumber(String FFANumber) {
        this.FFANumber = FFANumber;
    }

    public String getShipRegistration() {
        return shipRegistration;
    }

    public void setShipRegistration(String shipRegistration) {
        this.shipRegistration = shipRegistration;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIntegerernational() {
        return Integerernational;
    }

    public void setIntegerernational(String integerernational) {
        Integerernational = integerernational;
    }

    public String getFishNumber() {
        return fishNumber;
    }

    public void setFishNumber(String fishNumber) {
        this.fishNumber = fishNumber;
    }

    public String getUnLoading() {
        return unLoading;
    }

    public void setUnLoading(String unLoading) {
        this.unLoading = unLoading;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getPlaceUnload() {
        return placeUnload;
    }

    public void setPlaceUnload(String placeUnload) {
        this.placeUnload = placeUnload;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(String departuretime) {
        this.departuretime = departuretime;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getPcTime() {
        return pcTime;
    }

    public void setPcTime(String pcTime) {
        this.pcTime = pcTime;
    }

    public String getExporttime() {
        return exporttime;
    }

    public void setExporttime(String exporttime) {
        this.exporttime = exporttime;
    }

    public Integer getAlbacore() {
        return albacore;
    }

    public void setAlbacore(Integer albacore) {
        this.albacore = albacore;
    }

    public Integer getYellowfin() {
        return yellowfin;
    }

    public void setYellowfin(Integer yellowfin) {
        this.yellowfin = yellowfin;
    }

    public Integer getYellowfin2() {
        return yellowfin2;
    }

    public void setYellowfin2(Integer yellowfin2) {
        this.yellowfin2 = yellowfin2;
    }

    public Integer getYellowfin3() {
        return yellowfin3;
    }

    public void setYellowfin3(Integer yellowfin3) {
        this.yellowfin3 = yellowfin3;
    }

    public Integer getBigeye() {
        return bigeye;
    }

    public void setBigeye(Integer bigeye) {
        this.bigeye = bigeye;
    }

    public Integer getBigeye2() {
        return bigeye2;
    }

    public void setBigeye2(Integer bigeye2) {
        this.bigeye2 = bigeye2;
    }

    public Integer getBigeye3() {
        return bigeye3;
    }

    public void setBigeye3(Integer bigeye3) {
        this.bigeye3 = bigeye3;
    }

    public Integer getBigeye4() {
        return bigeye4;
    }

    public void setBigeye4(Integer bigeye4) {
        this.bigeye4 = bigeye4;
    }

    public Integer getStriped() {
        return striped;
    }

    public void setStriped(Integer striped) {
        this.striped = striped;
    }

    public Integer getSwordfish() {
        return swordfish;
    }

    public void setSwordfish(Integer swordfish) {
        this.swordfish = swordfish;
    }

    public Integer getOilfish() {
        return oilfish;
    }

    public void setOilfish(Integer oilfish) {
        this.oilfish = oilfish;
    }

    public Integer getMahimahi() {
        return mahimahi;
    }

    public void setMahimahi(Integer mahimahi) {
        this.mahimahi = mahimahi;
    }

    public Integer getBluemarlin() {
        return bluemarlin;
    }

    public void setBluemarlin(Integer bluemarlin) {
        this.bluemarlin = bluemarlin;
    }

    public Integer getWahoo() {
        return wahoo;
    }

    public void setWahoo(Integer wahoo) {
        this.wahoo = wahoo;
    }

    public Integer getSkipjack() {
        return skipjack;
    }

    public void setSkipjack(Integer skipjack) {
        this.skipjack = skipjack;
    }

    public Integer getBlackmarlin() {
        return blackmarlin;
    }

    public void setBlackmarlin(Integer blackmarlin) {
        this.blackmarlin = blackmarlin;
    }

    public Integer getSailfish() {
        return sailfish;
    }

    public void setSailfish(Integer sailfish) {
        this.sailfish = sailfish;
    }

    public Integer getMoonfish() {
        return moonfish;
    }

    public void setMoonfish(Integer moonfish) {
        this.moonfish = moonfish;
    }

    public Integer getBlackpomfret() {
        return blackpomfret;
    }

    public void setBlackpomfret(Integer blackpomfret) {
        this.blackpomfret = blackpomfret;
    }

    public Integer getSevefish() {
        return sevefish;
    }

    public void setSevefish(Integer sevefish) {
        this.sevefish = sevefish;
    }

    public Integer getShark() {
        return shark;
    }

    public void setShark(Integer shark) {
        this.shark = shark;
    }

    public Integer getBycatch() {
        return bycatch;
    }

    public void setBycatch(Integer bycatch) {
        this.bycatch = bycatch;
    }

    public Integer getBlueshark() {
        return blueshark;
    }

    public void setBlueshark(Integer blueshark) {
        this.blueshark = blueshark;
    }

    public Integer getHammerhead() {
        return hammerhead;
    }

    public void setHammerhead(Integer hammerhead) {
        this.hammerhead = hammerhead;
    }

    public Integer getMakosharks() {
        return makosharks;
    }

    public void setMakosharks(Integer makosharks) {
        this.makosharks = makosharks;
    }

    public Integer getOceanic() {
        return oceanic;
    }

    public void setOceanic(Integer oceanic) {
        this.oceanic = oceanic;
    }

    public Integer getSilkyshark() {
        return silkyshark;
    }

    public void setSilkyshark(Integer silkyshark) {
        this.silkyshark = silkyshark;
    }

    public Integer getThreshersharks() {
        return threshersharks;
    }

    public void setThreshersharks(Integer threshersharks) {
        this.threshersharks = threshersharks;
    }

    public Integer getPacific() {
        return pacific;
    }

    public void setPacific(Integer pacific) {
        this.pacific = pacific;
    }

    public Double getAlbacore1() {
        return albacore1;
    }

    public void setAlbacore1(Double albacore1) {
        this.albacore1 = albacore1;
    }

    public Double getYellowfin1() {
        return yellowfin1;
    }

    public void setYellowfin1(Double yellowfin1) {
        this.yellowfin1 = yellowfin1;
    }

    public Double getYellowfin21() {
        return yellowfin21;
    }

    public void setYellowfin21(Double yellowfin21) {
        this.yellowfin21 = yellowfin21;
    }

    public Double getYellowfin31() {
        return yellowfin31;
    }

    public void setYellowfin31(Double yellowfin31) {
        this.yellowfin31 = yellowfin31;
    }

    public Double getBigeye1() {
        return bigeye1;
    }

    public void setBigeye1(Double bigeye1) {
        this.bigeye1 = bigeye1;
    }

    public Double getBigeye21() {
        return bigeye21;
    }

    public void setBigeye21(Double bigeye21) {
        this.bigeye21 = bigeye21;
    }

    public Double getBigeye31() {
        return bigeye31;
    }

    public void setBigeye31(Double bigeye31) {
        this.bigeye31 = bigeye31;
    }

    public Double getBigeye41() {
        return bigeye41;
    }

    public void setBigeye41(Double bigeye41) {
        this.bigeye41 = bigeye41;
    }

    public Double getStriped1() {
        return striped1;
    }

    public void setStriped1(Double striped1) {
        this.striped1 = striped1;
    }

    public Double getSwordfish1() {
        return swordfish1;
    }

    public void setSwordfish1(Double swordfish1) {
        this.swordfish1 = swordfish1;
    }

    public Double getOilfish1() {
        return oilfish1;
    }

    public void setOilfish1(Double oilfish1) {
        this.oilfish1 = oilfish1;
    }

    public Double getMahimahi1() {
        return mahimahi1;
    }

    public void setMahimahi1(Double mahimahi1) {
        this.mahimahi1 = mahimahi1;
    }

    public Double getBluemarlin1() {
        return bluemarlin1;
    }

    public void setBluemarlin1(Double bluemarlin1) {
        this.bluemarlin1 = bluemarlin1;
    }

    public Double getWahoo1() {
        return wahoo1;
    }

    public void setWahoo1(Double wahoo1) {
        this.wahoo1 = wahoo1;
    }

    public Double getSkipjack1() {
        return skipjack1;
    }

    public void setSkipjack1(Double skipjack1) {
        this.skipjack1 = skipjack1;
    }

    public Double getBlackmarlin1() {
        return blackmarlin1;
    }

    public void setBlackmarlin1(Double blackmarlin1) {
        this.blackmarlin1 = blackmarlin1;
    }

    public Double getSailfish1() {
        return sailfish1;
    }

    public void setSailfish1(Double sailfish1) {
        this.sailfish1 = sailfish1;
    }

    public Double getMoonfish1() {
        return moonfish1;
    }

    public void setMoonfish1(Double moonfish1) {
        this.moonfish1 = moonfish1;
    }

    public Double getBlackpomfret1() {
        return blackpomfret1;
    }

    public void setBlackpomfret1(Double blackpomfret1) {
        this.blackpomfret1 = blackpomfret1;
    }

    public Double getSevefish1() {
        return sevefish1;
    }

    public void setSevefish1(Double sevefish1) {
        this.sevefish1 = sevefish1;
    }

    public Double getShark1() {
        return shark1;
    }

    public void setShark1(Double shark1) {
        this.shark1 = shark1;
    }

    public Double getBycatch1() {
        return bycatch1;
    }

    public void setBycatch1(Double bycatch1) {
        this.bycatch1 = bycatch1;
    }

    public Double getBlueshark1() {
        return blueshark1;
    }

    public void setBlueshark1(Double blueshark1) {
        this.blueshark1 = blueshark1;
    }

    public Double getHammerhead1() {
        return hammerhead1;
    }

    public void setHammerhead1(Double hammerhead1) {
        this.hammerhead1 = hammerhead1;
    }

    public Double getMakosharks1() {
        return makosharks1;
    }

    public void setMakosharks1(Double makosharks1) {
        this.makosharks1 = makosharks1;
    }

    public Double getOceanic1() {
        return oceanic1;
    }

    public void setOceanic1(Double oceanic1) {
        this.oceanic1 = oceanic1;
    }

    public Double getSilkyshark1() {
        return silkyshark1;
    }

    public void setSilkyshark1(Double silkyshark1) {
        this.silkyshark1 = silkyshark1;
    }

    public Double getThreshersharks1() {
        return threshersharks1;
    }

    public void setThreshersharks1(Double threshersharks1) {
        this.threshersharks1 = threshersharks1;
    }

    public Double getPacific1() {
        return pacific1;
    }

    public void setPacific1(Double pacific1) {
        this.pacific1 = pacific1;
    }

    public Integer getAlbacore2() {
        return albacore2;
    }

    public void setAlbacore2(Integer albacore2) {
        this.albacore2 = albacore2;
    }

    public Integer getYellowfin222() {
        return yellowfin222;
    }

    public void setYellowfin222(Integer yellowfin222) {
        this.yellowfin222 = yellowfin222;
    }

    public Integer getYellowfin22() {
        return yellowfin22;
    }

    public void setYellowfin22(Integer yellowfin22) {
        this.yellowfin22 = yellowfin22;
    }

    public Integer getYellowfin32() {
        return yellowfin32;
    }

    public void setYellowfin32(Integer yellowfin32) {
        this.yellowfin32 = yellowfin32;
    }

    public Integer getBigeye222() {
        return bigeye222;
    }

    public void setBigeye222(Integer bigeye222) {
        this.bigeye222 = bigeye222;
    }

    public Integer getBigeye22() {
        return bigeye22;
    }

    public void setBigeye22(Integer bigeye22) {
        this.bigeye22 = bigeye22;
    }

    public Integer getBigeye32() {
        return bigeye32;
    }

    public void setBigeye32(Integer bigeye32) {
        this.bigeye32 = bigeye32;
    }

    public Integer getBigeye42() {
        return bigeye42;
    }

    public void setBigeye42(Integer bigeye42) {
        this.bigeye42 = bigeye42;
    }

    public Integer getStriped2() {
        return striped2;
    }

    public void setStriped2(Integer striped2) {
        this.striped2 = striped2;
    }

    public Integer getSwordfish2() {
        return swordfish2;
    }

    public void setSwordfish2(Integer swordfish2) {
        this.swordfish2 = swordfish2;
    }

    public Integer getOilfish2() {
        return oilfish2;
    }

    public void setOilfish2(Integer oilfish2) {
        this.oilfish2 = oilfish2;
    }

    public Integer getMahimahi2() {
        return mahimahi2;
    }

    public void setMahimahi2(Integer mahimahi2) {
        this.mahimahi2 = mahimahi2;
    }

    public Integer getBluemarlin2() {
        return bluemarlin2;
    }

    public void setBluemarlin2(Integer bluemarlin2) {
        this.bluemarlin2 = bluemarlin2;
    }

    public Integer getWahoo2() {
        return wahoo2;
    }

    public void setWahoo2(Integer wahoo2) {
        this.wahoo2 = wahoo2;
    }

    public Integer getSkipjack2() {
        return skipjack2;
    }

    public void setSkipjack2(Integer skipjack2) {
        this.skipjack2 = skipjack2;
    }

    public Integer getBlackmarlin2() {
        return blackmarlin2;
    }

    public void setBlackmarlin2(Integer blackmarlin2) {
        this.blackmarlin2 = blackmarlin2;
    }

    public Integer getSailfish2() {
        return sailfish2;
    }

    public void setSailfish2(Integer sailfish2) {
        this.sailfish2 = sailfish2;
    }

    public Integer getMoonfish2() {
        return moonfish2;
    }

    public void setMoonfish2(Integer moonfish2) {
        this.moonfish2 = moonfish2;
    }

    public Integer getBlackpomfret2() {
        return blackpomfret2;
    }

    public void setBlackpomfret2(Integer blackpomfret2) {
        this.blackpomfret2 = blackpomfret2;
    }

    public Integer getSevefish2() {
        return sevefish2;
    }

    public void setSevefish2(Integer sevefish2) {
        this.sevefish2 = sevefish2;
    }

    public Integer getShark2() {
        return shark2;
    }

    public void setShark2(Integer shark2) {
        this.shark2 = shark2;
    }

    public Integer getBycatch2() {
        return bycatch2;
    }

    public void setBycatch2(Integer bycatch2) {
        this.bycatch2 = bycatch2;
    }

    public Integer getBlueshark2() {
        return blueshark2;
    }

    public void setBlueshark2(Integer blueshark2) {
        this.blueshark2 = blueshark2;
    }

    public Integer getHammerhead2() {
        return hammerhead2;
    }

    public void setHammerhead2(Integer hammerhead2) {
        this.hammerhead2 = hammerhead2;
    }

    public Integer getMakosharks2() {
        return makosharks2;
    }

    public void setMakosharks2(Integer makosharks2) {
        this.makosharks2 = makosharks2;
    }

    public Integer getOceanic2() {
        return oceanic2;
    }

    public void setOceanic2(Integer oceanic2) {
        this.oceanic2 = oceanic2;
    }

    public Integer getSilkyshark2() {
        return silkyshark2;
    }

    public void setSilkyshark2(Integer silkyshark2) {
        this.silkyshark2 = silkyshark2;
    }

    public Integer getThreshersharks2() {
        return threshersharks2;
    }

    public void setThreshersharks2(Integer threshersharks2) {
        this.threshersharks2 = threshersharks2;
    }

    public Integer getPacific2() {
        return pacific2;
    }

    public void setPacific2(Integer pacific2) {
        this.pacific2 = pacific2;
    }
}
