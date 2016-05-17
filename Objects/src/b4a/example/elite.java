package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class elite {
private static elite mostCurrent = new elite();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static b4a.example.math _m = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.dbadmin _dbadmin = null;
public b4a.example.galaxymaint _galaxymaint = null;
public b4a.example.systemsmaint _systemsmaint = null;
public b4a.example.stationsmaint _stationsmaint = null;
public b4a.example.stockmarketmaint _stockmarketmaint = null;
public b4a.example.splash _splash = null;
public b4a.example.functions _functions = null;
public b4a.example.dbutils _dbutils = null;
public b4a.example.sqlutils _sqlutils = null;
public b4a.example.commodupdate _commodupdate = null;
public b4a.example.edtables _edtables = null;
public b4a.example.anchordefine _anchordefine = null;
public static double  _checkdist(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _p1,b4a.example.coordinate _p2,double _dist) throws Exception{
int _dp = 0;
b4a.example.coordinate _v = null;
float _e = 0f;
double _resultdist = 0;
double _error = 0;
 //BA.debugLineNum = 377;BA.debugLine="Sub checkDist(p1 As Coordinate, p2 As Coordinate,";
 //BA.debugLineNum = 378;BA.debugLine="Dim dp As Int = 2";
_dp = (int) (2);
 //BA.debugLineNum = 379;BA.debugLine="Dim v As Coordinate = diff(p2, p1)";
_v = _diff(_ba,_p2,_p1);
 //BA.debugLineNum = 380;BA.debugLine="Dim e As Float = fround(Sqrt(fround(fround(fround";
_e = _fround(_ba,anywheresoftware.b4a.keywords.Common.Sqrt(_fround(_ba,_fround(_ba,_fround(_ba,_v._getx()*_v._getx())+_fround(_ba,_v._gety()*_v._gety()))+_fround(_ba,_v._getz()*_v._getz()))));
 //BA.debugLineNum = 381;BA.debugLine="Dim resultdist As Double = Round2(e, dp)";
_resultdist = anywheresoftware.b4a.keywords.Common.Round2(_e,_dp);
 //BA.debugLineNum = 382;BA.debugLine="Dim error As Double = Abs(resultdist - dist)";
_error = anywheresoftware.b4a.keywords.Common.Abs(_resultdist-_dist);
 //BA.debugLineNum = 383;BA.debugLine="Return error * error";
if (true) return _error*_error;
 //BA.debugLineNum = 384;BA.debugLine="End Sub";
return 0;
}
public static b4a.example.coordinate  _crossprod(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _p1,b4a.example.coordinate _p2) throws Exception{
b4a.example.coordinate _result = null;
 //BA.debugLineNum = 365;BA.debugLine="Sub crossProd(p1 As Coordinate, p2 As Coordinate)";
 //BA.debugLineNum = 366;BA.debugLine="Dim result As Coordinate";
_result = new b4a.example.coordinate();
 //BA.debugLineNum = 367;BA.debugLine="result.Initialize(p1.Y * p2.Z - p1.Z * p2.Y, p1.Z";
_result._initialize((_ba.processBA == null ? _ba : _ba.processBA),_p1._gety()*_p2._getz()-_p1._getz()*_p2._gety(),_p1._getz()*_p2._getx()-_p1._getx()*_p2._getz(),_p1._getx()*_p2._gety()-_p1._gety()*_p2._getx());
 //BA.debugLineNum = 368;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 369;BA.debugLine="End Sub";
return null;
}
public static b4a.example.coordinate  _diff(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _p1,b4a.example.coordinate _p2) throws Exception{
b4a.example.coordinate _result = null;
 //BA.debugLineNum = 349;BA.debugLine="Sub diff(p1 As Coordinate, p2 As Coordinate) As Co";
 //BA.debugLineNum = 354;BA.debugLine="Dim result As Coordinate";
_result = new b4a.example.coordinate();
 //BA.debugLineNum = 355;BA.debugLine="result.Initialize(p1.X - p2.X, p1.Y - p2.Y, p1.Z";
_result._initialize((_ba.processBA == null ? _ba : _ba.processBA),_p1._getx()-_p2._getx(),_p1._gety()-_p2._gety(),_p1._getz()-_p2._getz());
 //BA.debugLineNum = 356;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return null;
}
public static double  _distancebetween(anywheresoftware.b4a.BA _ba,String _destination,String _current) throws Exception{
String _query = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _cds = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _ccs = null;
double _result = 0;
 //BA.debugLineNum = 40;BA.debugLine="Sub DistanceBetween(Destination As String, Current";
 //BA.debugLineNum = 41;BA.debugLine="Dim Query As String";
_query = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim CDS As Cursor, CCS As Cursor";
_cds = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_ccs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim result As Double";
_result = 0;
 //BA.debugLineNum = 45;BA.debugLine="Query = \"SELECT * FROM Systems WHERE SystemName =";
_query = "SELECT * FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 46;BA.debugLine="CDS = Starter.SQLExec.ExecQuery2(Query,Array As S";
_cds.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_query,new String[]{_destination})));
 //BA.debugLineNum = 47;BA.debugLine="If CDS.RowCount = 0 Then";
if (_cds.getRowCount()==0) { 
 //BA.debugLineNum = 48;BA.debugLine="Return 0";
if (true) return 0;
 };
 //BA.debugLineNum = 51;BA.debugLine="Query = \"SELECT * FROM Systems WHERE SystemName =";
_query = "SELECT * FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 52;BA.debugLine="CCS = Starter.SQLExec.ExecQuery2(Query,Array As S";
_ccs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_query,new String[]{_current})));
 //BA.debugLineNum = 53;BA.debugLine="If CCS.RowCount = 0 Then";
if (_ccs.getRowCount()==0) { 
 //BA.debugLineNum = 54;BA.debugLine="Return 0";
if (true) return 0;
 };
 //BA.debugLineNum = 57;BA.debugLine="CDS.Position = 0";
_cds.setPosition((int) (0));
 //BA.debugLineNum = 58;BA.debugLine="CCS.Position = 0";
_ccs.setPosition((int) (0));
 //BA.debugLineNum = 60;BA.debugLine="result = LYFrom(CDS.GetDouble(\"SpaceX\"),CDS.GetDo";
_result = _lyfrom(_ba,_cds.GetDouble("SpaceX"),_cds.GetDouble("SpaceY"),_cds.GetDouble("SpaceZ"),_ccs.GetDouble("SpaceX"),_ccs.GetDouble("SpaceY"),_ccs.GetDouble("SpaceZ"));
 //BA.debugLineNum = 62;BA.debugLine="CDS.Close";
_cds.Close();
 //BA.debugLineNum = 63;BA.debugLine="CCS.Close";
_ccs.Close();
 //BA.debugLineNum = 65;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return 0;
}
public static double  _dotprod(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _p1,b4a.example.coordinate _p2) throws Exception{
 //BA.debugLineNum = 335;BA.debugLine="Sub dotProd(p1 As Coordinate, p2 As Coordinate) As";
 //BA.debugLineNum = 339;BA.debugLine="Return p1.X * p2.X + p1.Y * p2.Y + p1.Z * p2.Z";
if (true) return _p1._getx()*_p2._getx()+_p1._gety()*_p2._gety()+_p1._getz()*_p2._getz();
 //BA.debugLineNum = 340;BA.debugLine="End Sub";
return 0;
}
public static double  _eddist(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _p1,b4a.example.coordinate _p2,int _dp) throws Exception{
b4a.example.coordinate _v = null;
float _d = 0f;
double _resultdist = 0;
 //BA.debugLineNum = 386;BA.debugLine="Sub EDDist(p1 As Coordinate, p2 As Coordinate, dp";
 //BA.debugLineNum = 387;BA.debugLine="Dim v As Coordinate = diff(p2, p1)";
_v = _diff(_ba,_p2,_p1);
 //BA.debugLineNum = 388;BA.debugLine="Dim d As Float = fround(Sqrt(fround(fround(fround";
_d = _fround(_ba,anywheresoftware.b4a.keywords.Common.Sqrt(_fround(_ba,_fround(_ba,_fround(_ba,_v._getx()*_v._getx())+_fround(_ba,_v._gety()*_v._gety()))+_fround(_ba,_v._getz()*_v._getz()))));
 //BA.debugLineNum = 389;BA.debugLine="Dim resultdist As Double = Round2(d, dp)";
_resultdist = anywheresoftware.b4a.keywords.Common.Round2(_d,_dp);
 //BA.debugLineNum = 390;BA.debugLine="Return resultdist";
if (true) return _resultdist;
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return 0;
}
public static String  _findeconomy(anywheresoftware.b4a.BA _ba,int _indexnum) throws Exception{
int _i = 0;
String _query = "";
String _result = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _curseconomylist = null;
anywheresoftware.b4a.sql.SQL _sqlexec = null;
 //BA.debugLineNum = 403;BA.debugLine="Sub FindEconomy(IndexNum As Int) As String";
 //BA.debugLineNum = 404;BA.debugLine="Dim I As Int";
_i = 0;
 //BA.debugLineNum = 405;BA.debugLine="Dim Query As String";
_query = "";
 //BA.debugLineNum = 406;BA.debugLine="Dim result As String";
_result = "";
 //BA.debugLineNum = 407;BA.debugLine="Dim CursEconomyList As Cursor";
_curseconomylist = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 408;BA.debugLine="Dim SQLExec As SQL";
_sqlexec = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 410;BA.debugLine="SQLExec.Initialize(File.DirRootExternal, \"EliteTr";
_sqlexec.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 412;BA.debugLine="result = \"\"";
_result = "";
 //BA.debugLineNum = 413;BA.debugLine="Query = \"SELECT * FROM Economies ORDER BY Economy";
_query = "SELECT * FROM Economies ORDER BY EconomyID ASC";
 //BA.debugLineNum = 414;BA.debugLine="CursEconomyList = SQLExec.ExecQuery(Query)";
_curseconomylist.setObject((android.database.Cursor)(_sqlexec.ExecQuery(_query)));
 //BA.debugLineNum = 415;BA.debugLine="If CursEconomyList.RowCount > 0 Then";
if (_curseconomylist.getRowCount()>0) { 
 //BA.debugLineNum = 416;BA.debugLine="For I = 0 To CursEconomyList.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_curseconomylist.getRowCount()-1);
for (_i = (int) (0) ; (step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11); _i = ((int)(0 + _i + step11)) ) {
 //BA.debugLineNum = 417;BA.debugLine="CursEconomyList.Position = I";
_curseconomylist.setPosition(_i);
 //BA.debugLineNum = 418;BA.debugLine="If IndexNum = CursEconomyList.GetInt(\"EconomyID";
if (_indexnum==_curseconomylist.GetInt("EconomyID")) { 
 //BA.debugLineNum = 419;BA.debugLine="result = CursEconomyList.GetString(\"EconomyDes";
_result = _curseconomylist.GetString("EconomyDesc");
 //BA.debugLineNum = 420;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 //BA.debugLineNum = 424;BA.debugLine="CursEconomyList.Close";
_curseconomylist.Close();
 //BA.debugLineNum = 425;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 427;BA.debugLine="End Sub";
return "";
}
public static String  _findeconomynames(anywheresoftware.b4a.BA _ba,int _econint) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _result = null;
 //BA.debugLineNum = 429;BA.debugLine="Sub FindEconomyNames(EconInt As Int) As String";
 //BA.debugLineNum = 430;BA.debugLine="Dim result As StringBuilder";
_result = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 431;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 432;BA.debugLine="If EconInt > 524287 Then";
if (_econint>524287) { 
 //BA.debugLineNum = 433;BA.debugLine="result.Append(FindEconomy(524288))";
_result.Append(_findeconomy(_ba,(int) (524288)));
 //BA.debugLineNum = 434;BA.debugLine="EconInt = EconInt - 524288";
_econint = (int) (_econint-524288);
 };
 //BA.debugLineNum = 436;BA.debugLine="If EconInt > 262143 Then";
if (_econint>262143) { 
 //BA.debugLineNum = 437;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 438;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 440;BA.debugLine="result.Append(FindEconomy(262144))";
_result.Append(_findeconomy(_ba,(int) (262144)));
 //BA.debugLineNum = 441;BA.debugLine="EconInt = EconInt - 262144";
_econint = (int) (_econint-262144);
 };
 //BA.debugLineNum = 443;BA.debugLine="If EconInt > 131071 Then";
if (_econint>131071) { 
 //BA.debugLineNum = 444;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 445;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 447;BA.debugLine="result.Append(FindEconomy(131072))";
_result.Append(_findeconomy(_ba,(int) (131072)));
 //BA.debugLineNum = 448;BA.debugLine="EconInt = EconInt - 131072";
_econint = (int) (_econint-131072);
 };
 //BA.debugLineNum = 450;BA.debugLine="If EconInt > 65535 Then";
if (_econint>65535) { 
 //BA.debugLineNum = 451;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 452;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 454;BA.debugLine="result.Append(FindEconomy(65536))";
_result.Append(_findeconomy(_ba,(int) (65536)));
 //BA.debugLineNum = 455;BA.debugLine="EconInt = EconInt - 65536";
_econint = (int) (_econint-65536);
 };
 //BA.debugLineNum = 457;BA.debugLine="If EconInt > 32767 Then";
if (_econint>32767) { 
 //BA.debugLineNum = 458;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 459;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 461;BA.debugLine="result.Append(FindEconomy(32768))";
_result.Append(_findeconomy(_ba,(int) (32768)));
 //BA.debugLineNum = 462;BA.debugLine="EconInt = EconInt - 32768";
_econint = (int) (_econint-32768);
 };
 //BA.debugLineNum = 464;BA.debugLine="If EconInt > 16383 Then";
if (_econint>16383) { 
 //BA.debugLineNum = 465;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 466;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 468;BA.debugLine="result.Append(FindEconomy(16384))";
_result.Append(_findeconomy(_ba,(int) (16384)));
 //BA.debugLineNum = 469;BA.debugLine="EconInt = EconInt - 16384";
_econint = (int) (_econint-16384);
 };
 //BA.debugLineNum = 471;BA.debugLine="If EconInt > 8191 Then";
if (_econint>8191) { 
 //BA.debugLineNum = 472;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 473;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 475;BA.debugLine="result.Append(FindEconomy(8192))";
_result.Append(_findeconomy(_ba,(int) (8192)));
 //BA.debugLineNum = 476;BA.debugLine="EconInt = EconInt - 8192";
_econint = (int) (_econint-8192);
 };
 //BA.debugLineNum = 478;BA.debugLine="If EconInt > 4095 Then";
if (_econint>4095) { 
 //BA.debugLineNum = 479;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 480;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 482;BA.debugLine="result.Append(FindEconomy(4096))";
_result.Append(_findeconomy(_ba,(int) (4096)));
 //BA.debugLineNum = 483;BA.debugLine="EconInt = EconInt - 4096";
_econint = (int) (_econint-4096);
 };
 //BA.debugLineNum = 485;BA.debugLine="If EconInt > 2047 Then";
if (_econint>2047) { 
 //BA.debugLineNum = 486;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 487;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 489;BA.debugLine="result.Append(FindEconomy(2048))";
_result.Append(_findeconomy(_ba,(int) (2048)));
 //BA.debugLineNum = 490;BA.debugLine="EconInt = EconInt - 2048";
_econint = (int) (_econint-2048);
 };
 //BA.debugLineNum = 492;BA.debugLine="If EconInt > 1023 Then";
if (_econint>1023) { 
 //BA.debugLineNum = 493;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 494;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 496;BA.debugLine="result.Append(FindEconomy(1024))";
_result.Append(_findeconomy(_ba,(int) (1024)));
 //BA.debugLineNum = 497;BA.debugLine="EconInt = EconInt - 1024";
_econint = (int) (_econint-1024);
 };
 //BA.debugLineNum = 499;BA.debugLine="If EconInt > 511 Then";
if (_econint>511) { 
 //BA.debugLineNum = 500;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 501;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 503;BA.debugLine="result.Append(FindEconomy(512))";
_result.Append(_findeconomy(_ba,(int) (512)));
 //BA.debugLineNum = 504;BA.debugLine="EconInt = EconInt - 512";
_econint = (int) (_econint-512);
 };
 //BA.debugLineNum = 506;BA.debugLine="If EconInt > 255 Then";
if (_econint>255) { 
 //BA.debugLineNum = 507;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 508;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 510;BA.debugLine="result.Append(FindEconomy(256))";
_result.Append(_findeconomy(_ba,(int) (256)));
 //BA.debugLineNum = 511;BA.debugLine="EconInt = EconInt - 256";
_econint = (int) (_econint-256);
 };
 //BA.debugLineNum = 513;BA.debugLine="If EconInt > 127 Then";
if (_econint>127) { 
 //BA.debugLineNum = 514;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 515;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 517;BA.debugLine="result.Append(FindEconomy(128))";
_result.Append(_findeconomy(_ba,(int) (128)));
 //BA.debugLineNum = 518;BA.debugLine="EconInt = EconInt - 128";
_econint = (int) (_econint-128);
 };
 //BA.debugLineNum = 520;BA.debugLine="If EconInt > 63 Then";
if (_econint>63) { 
 //BA.debugLineNum = 521;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 522;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 524;BA.debugLine="result.Append(FindEconomy(64))";
_result.Append(_findeconomy(_ba,(int) (64)));
 //BA.debugLineNum = 525;BA.debugLine="EconInt = EconInt - 64";
_econint = (int) (_econint-64);
 };
 //BA.debugLineNum = 527;BA.debugLine="If EconInt > 31 Then";
if (_econint>31) { 
 //BA.debugLineNum = 528;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 529;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 531;BA.debugLine="result.Append(FindEconomy(32))";
_result.Append(_findeconomy(_ba,(int) (32)));
 //BA.debugLineNum = 532;BA.debugLine="EconInt = EconInt - 32";
_econint = (int) (_econint-32);
 };
 //BA.debugLineNum = 534;BA.debugLine="If EconInt > 15 Then";
if (_econint>15) { 
 //BA.debugLineNum = 535;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 536;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 538;BA.debugLine="result.Append(FindEconomy(16))";
_result.Append(_findeconomy(_ba,(int) (16)));
 //BA.debugLineNum = 539;BA.debugLine="EconInt = EconInt - 16";
_econint = (int) (_econint-16);
 };
 //BA.debugLineNum = 541;BA.debugLine="If EconInt > 7 Then";
if (_econint>7) { 
 //BA.debugLineNum = 542;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 543;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 545;BA.debugLine="result.Append(FindEconomy(8))";
_result.Append(_findeconomy(_ba,(int) (8)));
 //BA.debugLineNum = 546;BA.debugLine="EconInt = EconInt - 8";
_econint = (int) (_econint-8);
 };
 //BA.debugLineNum = 548;BA.debugLine="If EconInt > 3 Then";
if (_econint>3) { 
 //BA.debugLineNum = 549;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 550;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 552;BA.debugLine="result.Append(FindEconomy(4))";
_result.Append(_findeconomy(_ba,(int) (4)));
 //BA.debugLineNum = 553;BA.debugLine="EconInt = EconInt - 4";
_econint = (int) (_econint-4);
 };
 //BA.debugLineNum = 555;BA.debugLine="If EconInt > 1 Then";
if (_econint>1) { 
 //BA.debugLineNum = 556;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 557;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 559;BA.debugLine="result.Append(FindEconomy(2))";
_result.Append(_findeconomy(_ba,(int) (2)));
 //BA.debugLineNum = 560;BA.debugLine="EconInt = EconInt - 2";
_econint = (int) (_econint-2);
 };
 //BA.debugLineNum = 562;BA.debugLine="If EconInt > 0 Then";
if (_econint>0) { 
 //BA.debugLineNum = 563;BA.debugLine="If result.Length > 0 Then";
if (_result.getLength()>0) { 
 //BA.debugLineNum = 564;BA.debugLine="result.Append(\", \")";
_result.Append(", ");
 };
 //BA.debugLineNum = 566;BA.debugLine="result.Append(FindEconomy(1))";
_result.Append(_findeconomy(_ba,(int) (1)));
 //BA.debugLineNum = 567;BA.debugLine="EconInt = EconInt - 1";
_econint = (int) (_econint-1);
 };
 //BA.debugLineNum = 569;BA.debugLine="Return result";
if (true) return BA.ObjectToString(_result);
 //BA.debugLineNum = 570;BA.debugLine="End Sub";
return "";
}
public static int  _findeconomynum(anywheresoftware.b4a.BA _ba,String _economyname) throws Exception{
int _i = 0;
String _query = "";
int _result = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curseconomylist = null;
anywheresoftware.b4a.sql.SQL _sqlexec = null;
 //BA.debugLineNum = 572;BA.debugLine="Sub FindEconomyNum(EconomyName As String) As Int";
 //BA.debugLineNum = 573;BA.debugLine="Dim I As Int";
_i = 0;
 //BA.debugLineNum = 574;BA.debugLine="Dim Query As String";
_query = "";
 //BA.debugLineNum = 575;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 576;BA.debugLine="Dim CursEconomyList As Cursor";
_curseconomylist = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 577;BA.debugLine="Dim SQLExec As SQL";
_sqlexec = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 579;BA.debugLine="SQLExec.Initialize(File.DirRootExternal, \"EliteTr";
_sqlexec.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 581;BA.debugLine="result = 0";
_result = (int) (0);
 //BA.debugLineNum = 582;BA.debugLine="Query = \"SELECT * FROM Economies ORDER BY Economy";
_query = "SELECT * FROM Economies ORDER BY EconomyID ASC";
 //BA.debugLineNum = 583;BA.debugLine="CursEconomyList = SQLExec.ExecQuery(Query)";
_curseconomylist.setObject((android.database.Cursor)(_sqlexec.ExecQuery(_query)));
 //BA.debugLineNum = 584;BA.debugLine="If CursEconomyList.RowCount > 0 Then";
if (_curseconomylist.getRowCount()>0) { 
 //BA.debugLineNum = 585;BA.debugLine="For I = 0 To CursEconomyList.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_curseconomylist.getRowCount()-1);
for (_i = (int) (0) ; (step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11); _i = ((int)(0 + _i + step11)) ) {
 //BA.debugLineNum = 586;BA.debugLine="CursEconomyList.Position = I";
_curseconomylist.setPosition(_i);
 //BA.debugLineNum = 587;BA.debugLine="If EconomyName = CursEconomyList.GetString(\"Eco";
if ((_economyname).equals(_curseconomylist.GetString("EconomyDesc"))) { 
 //BA.debugLineNum = 588;BA.debugLine="result = CursEconomyList.GetInt(\"EconomyID\")";
_result = _curseconomylist.GetInt("EconomyID");
 //BA.debugLineNum = 589;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 //BA.debugLineNum = 593;BA.debugLine="CursEconomyList.Close";
_curseconomylist.Close();
 //BA.debugLineNum = 594;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 596;BA.debugLine="End Sub";
return 0;
}
public static float  _fround(anywheresoftware.b4a.BA _ba,double _d) throws Exception{
float _f = 0f;
 //BA.debugLineNum = 393;BA.debugLine="Sub fround(d As Double) As Float";
 //BA.debugLineNum = 394;BA.debugLine="Dim f As Float";
_f = 0f;
 //BA.debugLineNum = 395;BA.debugLine="f = d";
_f = (float) (_d);
 //BA.debugLineNum = 396;BA.debugLine="Return f";
if (true) return _f;
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return 0f;
}
public static double  _length(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _v) throws Exception{
 //BA.debugLineNum = 342;BA.debugLine="Sub length(v As Coordinate) As Double";
 //BA.debugLineNum = 346;BA.debugLine="Return Sqrt(dotProd(v,v))";
if (true) return anywheresoftware.b4a.keywords.Common.Sqrt(_dotprod(_ba,_v,_v));
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return 0;
}
public static double  _lyfrom(anywheresoftware.b4a.BA _ba,double _destinationx,double _destinationy,double _destinationz,double _currentx,double _currenty,double _currentz) throws Exception{
double _calcx = 0;
double _calcy = 0;
double _calcz = 0;
double _lyresult = 0;
 //BA.debugLineNum = 69;BA.debugLine="Sub LYFrom(DestinationX As Double, DestinationY As";
 //BA.debugLineNum = 70;BA.debugLine="Dim CalcX As Double, CalcY As Double, CalcZ As Do";
_calcx = 0;
_calcy = 0;
_calcz = 0;
 //BA.debugLineNum = 71;BA.debugLine="Dim LYResult As Double";
_lyresult = 0;
 //BA.debugLineNum = 73;BA.debugLine="CalcX = DestinationX - CurrentX";
_calcx = _destinationx-_currentx;
 //BA.debugLineNum = 74;BA.debugLine="CalcY = DestinationY - CurrentY";
_calcy = _destinationy-_currenty;
 //BA.debugLineNum = 75;BA.debugLine="CalcZ = DestinationZ - CurrentZ";
_calcz = _destinationz-_currentz;
 //BA.debugLineNum = 77;BA.debugLine="LYResult = Sqrt((CalcX * CalcX) + (CalcY * CalcY)";
_lyresult = anywheresoftware.b4a.keywords.Common.Sqrt((_calcx*_calcx)+(_calcy*_calcy)+(_calcz*_calcz));
 //BA.debugLineNum = 79;BA.debugLine="Return Round2(LYResult,2) 'Returns value as 2 dec";
if (true) return anywheresoftware.b4a.keywords.Common.Round2(_lyresult,(int) (2));
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return 0;
}
public static b4a.example.coordinate  _plotnewsystem(anywheresoftware.b4a.BA _ba) throws Exception{
int _i = 0;
anywheresoftware.b4a.agraham.dialogs.InputDialog _dialog = null;
String _q = "";
String _rtnstatus = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursanchors = null;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
b4a.example.coordinate[] _coord = null;
anywheresoftware.b4a.objects.collections.List _systems = null;
anywheresoftware.b4a.objects.collections.List _regions = null;
int _aa = 0;
int _bb = 0;
int _cc = 0;
b4a.example.coordinate _p1p2 = null;
double _d = 0;
b4a.example.coordinate _ex = null;
b4a.example.coordinate _p1p3 = null;
double _j = 0;
b4a.example.coordinate _ey = null;
double _k = 0;
double _xx = 0;
double _yy = 0;
double _zsq = 0;
double _zz = 0;
b4a.example.coordinate _ez = null;
b4a.example.coordinate _coord1 = null;
b4a.example.coordinate _coord2 = null;
double _errorcount1 = 0;
double _errorcount2 = 0;
String _combination = "";
boolean _existregionfound = false;
b4a.example.region _r = null;
b4a.example.region _s = null;
b4a.example.region _testregion = null;
b4a.example.region _existingregion = null;
int _w = 0;
int _bestcount = 0;
int _nextcount = 0;
anywheresoftware.b4a.objects.collections.List _bestlist = null;
anywheresoftware.b4a.objects.collections.List _nextlist = null;
int _count = 0;
int _matches = 0;
double _x = 0;
double _y = 0;
double _z = 0;
anywheresoftware.b4a.objects.collections.List _highregions = null;
b4a.example.region _reg = null;
b4a.example.coordinate _p = null;
int _cyc = 0;
boolean _found = false;
b4a.example.coordinate _coo = null;
b4a.example.coordinate _bestcoord = null;
 //BA.debugLineNum = 84;BA.debugLine="Sub PlotNewSystem As Coordinate";
 //BA.debugLineNum = 85;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 86;BA.debugLine="Dim dialog As InputDialog";
_dialog = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 87;BA.debugLine="Dim Q As String, rtnstatus As String";
_q = "";
_rtnstatus = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim CursAnchors As Cursor";
_cursanchors = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 91;BA.debugLine="Q = \"SELECT AnchorName, SpaceX, SpaceY, SpaceZ, E";
_q = "SELECT AnchorName, SpaceX, SpaceY, SpaceZ, EnteredDistance FROM AnchorSystems ORDER BY Weight ASC";
 //BA.debugLineNum = 92;BA.debugLine="CursAnchors = Starter.SQLExec.ExecQuery(Q)";
_cursanchors.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 94;BA.debugLine="Dim coord(CursAnchors.RowCount - 1) As Coordinate";
_coord = new b4a.example.coordinate[(int) (_cursanchors.getRowCount()-1)];
{
int d0 = _coord.length;
for (int i0 = 0;i0 < d0;i0++) {
_coord[i0] = new b4a.example.coordinate();
}
}
;
 //BA.debugLineNum = 95;BA.debugLine="Dim systems As List";
_systems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 96;BA.debugLine="Dim regions As List";
_regions = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 97;BA.debugLine="systems.Initialize";
_systems.Initialize();
 //BA.debugLineNum = 98;BA.debugLine="regions.Initialize";
_regions.Initialize();
 //BA.debugLineNum = 100;BA.debugLine="For i = 0 To CursAnchors.RowCount - 1";
{
final int step13 = 1;
final int limit13 = (int) (_cursanchors.getRowCount()-1);
for (_i = (int) (0) ; (step13 > 0 && _i <= limit13) || (step13 < 0 && _i >= limit13); _i = ((int)(0 + _i + step13)) ) {
 //BA.debugLineNum = 101;BA.debugLine="CursAnchors.Position = i";
_cursanchors.setPosition(_i);
 //BA.debugLineNum = 102;BA.debugLine="dialog.InputType = dialog.INPUT_TYPE_DECIMAL_NUM";
_dialog.setInputType(_dialog.INPUT_TYPE_DECIMAL_NUMBERS);
 //BA.debugLineNum = 103;BA.debugLine="rtnstatus = dialog.Show(\"Distance from \" & CursA";
_rtnstatus = BA.NumberToString(_dialog.Show("Distance from "+_cursanchors.GetString("AnchorName")+" System","Locate "+_cursanchors.GetString("AnchorName").toUpperCase(),"Submit","Cancel","",_ba,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 104;BA.debugLine="If rtnstatus = -3 Then";
if ((_rtnstatus).equals(BA.NumberToString(-3))) { 
 //BA.debugLineNum = 105;BA.debugLine="Exit";
if (true) break;
 };
 //BA.debugLineNum = 107;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 108;BA.debugLine="whereclause.Put(\"AnchorName\", CursAnchors.GetStr";
_whereclause.Put((Object)("AnchorName"),(Object)(_cursanchors.GetString("AnchorName")));
 //BA.debugLineNum = 109;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"AnchorSys";
mostCurrent._dbutils._updaterecord(_ba,mostCurrent._starter._sqlexec,"AnchorSystems","EnteredDistance",(Object)(_dialog.getInput()),_whereclause);
 //BA.debugLineNum = 110;BA.debugLine="coord(i).Initialize(CursAnchors.GetDouble(\"Space";
_coord[_i]._initialize((_ba.processBA == null ? _ba : _ba.processBA),_cursanchors.GetDouble("SpaceX"),_cursanchors.GetDouble("SpaceY"),_cursanchors.GetDouble("SpaceZ"));
 //BA.debugLineNum = 111;BA.debugLine="coord(i).Dist = dialog.Input";
_coord[_i]._setdist((double)(Double.parseDouble(_dialog.getInput())));
 //BA.debugLineNum = 112;BA.debugLine="dialog.Input = \"\"";
_dialog.setInput("");
 //BA.debugLineNum = 115;BA.debugLine="If i >= 3 Then";
if (_i>=3) { 
 //BA.debugLineNum = 116;BA.debugLine="systems.Clear";
_systems.Clear();
 //BA.debugLineNum = 117;BA.debugLine="regions.Clear";
_regions.Clear();
 //BA.debugLineNum = 119;BA.debugLine="For aa = 0 To i";
{
final int step29 = 1;
final int limit29 = _i;
for (_aa = (int) (0) ; (step29 > 0 && _aa <= limit29) || (step29 < 0 && _aa >= limit29); _aa = ((int)(0 + _aa + step29)) ) {
 //BA.debugLineNum = 120;BA.debugLine="For bb = aa + 1 To i";
{
final int step30 = 1;
final int limit30 = _i;
for (_bb = (int) (_aa+1) ; (step30 > 0 && _bb <= limit30) || (step30 < 0 && _bb >= limit30); _bb = ((int)(0 + _bb + step30)) ) {
 //BA.debugLineNum = 121;BA.debugLine="For cc = bb + 1 To i";
{
final int step31 = 1;
final int limit31 = _i;
for (_cc = (int) (_bb+1) ; (step31 > 0 && _cc <= limit31) || (step31 < 0 && _cc >= limit31); _cc = ((int)(0 + _cc + step31)) ) {
 //BA.debugLineNum = 122;BA.debugLine="Log(\"Combination: \" & aa & \", \" & bb & \", \"";
anywheresoftware.b4a.keywords.Common.Log("Combination: "+BA.NumberToString(_aa)+", "+BA.NumberToString(_bb)+", "+BA.NumberToString(_cc));
 //BA.debugLineNum = 123;BA.debugLine="Log(\"   aa: \" & coord(aa).X & \", \" & coord(a";
anywheresoftware.b4a.keywords.Common.Log("   aa: "+BA.NumberToString(_coord[_aa]._getx())+", "+BA.NumberToString(_coord[_aa]._gety())+", "+BA.NumberToString(_coord[_aa]._getz())+" -- Dist: "+BA.NumberToString(_coord[_aa]._getdist()));
 //BA.debugLineNum = 124;BA.debugLine="Log(\"   bb: \" & coord(bb).X & \", \" & coord(b";
anywheresoftware.b4a.keywords.Common.Log("   bb: "+BA.NumberToString(_coord[_bb]._getx())+", "+BA.NumberToString(_coord[_bb]._gety())+", "+BA.NumberToString(_coord[_bb]._getz())+" -- Dist: "+BA.NumberToString(_coord[_bb]._getdist()));
 //BA.debugLineNum = 125;BA.debugLine="Log(\"   cc: \" & coord(cc).X & \", \" & coord(c";
anywheresoftware.b4a.keywords.Common.Log("   cc: "+BA.NumberToString(_coord[_cc]._getx())+", "+BA.NumberToString(_coord[_cc]._gety())+", "+BA.NumberToString(_coord[_cc]._getz())+" -- Dist: "+BA.NumberToString(_coord[_cc]._getdist()));
 //BA.debugLineNum = 127;BA.debugLine="Dim p1p2 As Coordinate = diff(coord(bb), coo";
_p1p2 = _diff(_ba,_coord[_bb],_coord[_aa]);
 //BA.debugLineNum = 128;BA.debugLine="Dim d As Double = length(p1p2)";
_d = _length(_ba,_p1p2);
 //BA.debugLineNum = 129;BA.debugLine="Dim ex As Coordinate = scalarProd(1 / d, p1p";
_ex = _scalarprod(_ba,1/(double)_d,_p1p2);
 //BA.debugLineNum = 130;BA.debugLine="Dim p1p3 As Coordinate = diff(coord(cc), coo";
_p1p3 = _diff(_ba,_coord[_cc],_coord[_aa]);
 //BA.debugLineNum = 131;BA.debugLine="Dim j As Double = dotProd(ex, p1p3)";
_j = _dotprod(_ba,_ex,_p1p3);
 //BA.debugLineNum = 132;BA.debugLine="Dim ey As Coordinate = diff(p1p3, scalarProd";
_ey = _diff(_ba,_p1p3,_scalarprod(_ba,_j,_ex));
 //BA.debugLineNum = 133;BA.debugLine="ey = scalarProd(1 / length(ey), ey)";
_ey = _scalarprod(_ba,1/(double)_length(_ba,_ey),_ey);
 //BA.debugLineNum = 134;BA.debugLine="Dim k As Double = dotProd(ey, diff(coord(cc)";
_k = _dotprod(_ba,_ey,_diff(_ba,_coord[_cc],_coord[_aa]));
 //BA.debugLineNum = 135;BA.debugLine="Dim xx As Double = (coord(aa).Dist * coord(a";
_xx = (_coord[_aa]._getdist()*_coord[_aa]._getdist()-_coord[_bb]._getdist()*_coord[_bb]._getdist()+_d*_d)/(double)(2*_d);
 //BA.debugLineNum = 136;BA.debugLine="Dim yy As Double = ((coord(aa).Dist * coord(";
_yy = ((_coord[_aa]._getdist()*_coord[_aa]._getdist()-_coord[_cc]._getdist()*_coord[_cc]._getdist()+_j*_j+_k*_k)/(double)(2*_k))-(_j*_xx/(double)_k);
 //BA.debugLineNum = 137;BA.debugLine="Dim zsq As Double = coord(aa).Dist * coord(a";
_zsq = _coord[_aa]._getdist()*_coord[_aa]._getdist()-_xx*_xx-_yy*_yy;
 //BA.debugLineNum = 138;BA.debugLine="If zsq > 0 Then";
if (_zsq>0) { 
 //BA.debugLineNum = 139;BA.debugLine="Dim zz As Double = Sqrt(zsq)";
_zz = anywheresoftware.b4a.keywords.Common.Sqrt(_zsq);
 //BA.debugLineNum = 140;BA.debugLine="Dim ez As Coordinate = crossProd(ex, ey)";
_ez = _crossprod(_ba,_ex,_ey);
 //BA.debugLineNum = 141;BA.debugLine="Dim coord1 As Coordinate = sum(sum(coord(aa";
_coord1 = _sum(_ba,_sum(_ba,_coord[_aa],_scalarprod(_ba,_xx,_ex)),_scalarprod(_ba,_yy,_ey));
 //BA.debugLineNum = 142;BA.debugLine="Dim coord2 As Coordinate = diff(coord1, sca";
_coord2 = _diff(_ba,_coord1,_scalarprod(_ba,_zz,_ez));
 //BA.debugLineNum = 143;BA.debugLine="coord1 = sum(coord1, scalarProd(zz, ez))";
_coord1 = _sum(_ba,_coord1,_scalarprod(_ba,_zz,_ez));
 //BA.debugLineNum = 144;BA.debugLine="Log(\"coord1: \" & coord1.X & \", \" & coord1.Y";
anywheresoftware.b4a.keywords.Common.Log("coord1: "+BA.NumberToString(_coord1._getx())+", "+BA.NumberToString(_coord1._gety())+", "+BA.NumberToString(_coord1._getz()));
 //BA.debugLineNum = 145;BA.debugLine="Log(\"coord2: \" & coord2.X & \", \" & coord2.Y";
anywheresoftware.b4a.keywords.Common.Log("coord2: "+BA.NumberToString(_coord2._getx())+", "+BA.NumberToString(_coord2._gety())+", "+BA.NumberToString(_coord2._getz()));
 //BA.debugLineNum = 148;BA.debugLine="Dim errorcount1 = 0, errorcount2 = 0 As Dou";
_errorcount1 = 0;
_errorcount2 = 0;
 //BA.debugLineNum = 149;BA.debugLine="errorcount1 = checkDist(coord1, coord(aa),";
_errorcount1 = _checkdist(_ba,_coord1,_coord[_aa],_coord[_aa]._getdist());
 //BA.debugLineNum = 150;BA.debugLine="coord1.TotSqrErr = coord1.TotSqrErr + error";
_coord1._settotsqrerr(_coord1._gettotsqrerr()+_errorcount1);
 //BA.debugLineNum = 152;BA.debugLine="errorcount1 = checkDist(coord1, coord(bb),";
_errorcount1 = _checkdist(_ba,_coord1,_coord[_bb],_coord[_bb]._getdist());
 //BA.debugLineNum = 153;BA.debugLine="coord1.TotSqrErr = coord1.TotSqrErr + error";
_coord1._settotsqrerr(_coord1._gettotsqrerr()+_errorcount1);
 //BA.debugLineNum = 155;BA.debugLine="errorcount1 = checkDist(coord1, coord(cc),";
_errorcount1 = _checkdist(_ba,_coord1,_coord[_cc],_coord[_cc]._getdist());
 //BA.debugLineNum = 156;BA.debugLine="coord1.TotSqrErr = coord1.TotSqrErr + error";
_coord1._settotsqrerr(_coord1._gettotsqrerr()+_errorcount1);
 //BA.debugLineNum = 158;BA.debugLine="errorcount2 = checkDist(coord2, coord(aa),";
_errorcount2 = _checkdist(_ba,_coord2,_coord[_aa],_coord[_aa]._getdist());
 //BA.debugLineNum = 159;BA.debugLine="coord2.TotSqrErr = coord2.TotSqrErr + error";
_coord2._settotsqrerr(_coord2._gettotsqrerr()+_errorcount2);
 //BA.debugLineNum = 161;BA.debugLine="errorcount2 = checkDist(coord2, coord(bb),";
_errorcount2 = _checkdist(_ba,_coord2,_coord[_bb],_coord[_bb]._getdist());
 //BA.debugLineNum = 162;BA.debugLine="coord2.TotSqrErr = coord2.TotSqrErr + error";
_coord2._settotsqrerr(_coord2._gettotsqrerr()+_errorcount2);
 //BA.debugLineNum = 164;BA.debugLine="errorcount2 = checkDist(coord2, coord(cc),";
_errorcount2 = _checkdist(_ba,_coord2,_coord[_cc],_coord[_cc]._getdist());
 //BA.debugLineNum = 165;BA.debugLine="coord2.TotSqrErr = coord2.TotSqrErr + error";
_coord2._settotsqrerr(_coord2._gettotsqrerr()+_errorcount2);
 //BA.debugLineNum = 167;BA.debugLine="Dim combination As String";
_combination = "";
 //BA.debugLineNum = 168;BA.debugLine="combination = aa & bb & cc";
_combination = BA.NumberToString(_aa)+BA.NumberToString(_bb)+BA.NumberToString(_cc);
 //BA.debugLineNum = 170;BA.debugLine="If systems.IndexOf(combination) = -1 Then";
if (_systems.IndexOf((Object)(_combination))==-1) { 
 //BA.debugLineNum = 172;BA.debugLine="Dim ExistRegionFound As Boolean";
_existregionfound = false;
 //BA.debugLineNum = 173;BA.debugLine="Dim r, s As Region";
_r = new b4a.example.region();
_s = new b4a.example.region();
 //BA.debugLineNum = 174;BA.debugLine="r.Initialize";
_r._initialize((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 175;BA.debugLine="s.Initialize";
_s._initialize((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 176;BA.debugLine="Dim testregion, existingregion As Region";
_testregion = new b4a.example.region();
_existingregion = new b4a.example.region();
 //BA.debugLineNum = 177;BA.debugLine="testregion.Initialize";
_testregion._initialize((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 178;BA.debugLine="existingregion.Initialize";
_existingregion._initialize((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 180;BA.debugLine="ExistRegionFound = False";
_existregionfound = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 181;BA.debugLine="r.setAll(coord1)";
_r._setall(_coord1);
 //BA.debugLineNum = 182;BA.debugLine="If regions.Size > 0 Then";
if (_regions.getSize()>0) { 
 //BA.debugLineNum = 183;BA.debugLine="For w = 0 To regions.Size - 1";
{
final int step81 = 1;
final int limit81 = (int) (_regions.getSize()-1);
for (_w = (int) (0) ; (step81 > 0 && _w <= limit81) || (step81 < 0 && _w >= limit81); _w = ((int)(0 + _w + step81)) ) {
 //BA.debugLineNum = 184;BA.debugLine="existingregion = regions.Get(w)";
_existingregion = (b4a.example.region)(_regions.Get(_w));
 //BA.debugLineNum = 185;BA.debugLine="testregion = r.union(existingregion)";
_testregion = _r._union(_existingregion);
 //BA.debugLineNum = 186;BA.debugLine="If testregion.volume < r.volume + existi";
if (_testregion._volume()<_r._volume()+_existingregion._volume()) { 
 //BA.debugLineNum = 188;BA.debugLine="testregion.setSysCount(existingregion.S";
_testregion._setsyscount((int) (_existingregion._getsyscount()+1));
 //BA.debugLineNum = 189;BA.debugLine="regions.RemoveAt(w)";
_regions.RemoveAt(_w);
 //BA.debugLineNum = 190;BA.debugLine="regions.Add(testregion)";
_regions.Add((Object)(_testregion));
 //BA.debugLineNum = 191;BA.debugLine="ExistRegionFound = True";
_existregionfound = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 192;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 //BA.debugLineNum = 196;BA.debugLine="If ExistRegionFound = False Then";
if (_existregionfound==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 198;BA.debugLine="r.setSysCount(1)";
_r._setsyscount((int) (1));
 //BA.debugLineNum = 199;BA.debugLine="regions.Add(r)";
_regions.Add((Object)(_r));
 };
 //BA.debugLineNum = 202;BA.debugLine="ExistRegionFound = False";
_existregionfound = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 203;BA.debugLine="s.setAll(coord2)";
_s._setall(_coord2);
 //BA.debugLineNum = 204;BA.debugLine="If regions.Size > 0 Then";
if (_regions.getSize()>0) { 
 //BA.debugLineNum = 205;BA.debugLine="For w = 0 To regions.Size - 1";
{
final int step100 = 1;
final int limit100 = (int) (_regions.getSize()-1);
for (_w = (int) (0) ; (step100 > 0 && _w <= limit100) || (step100 < 0 && _w >= limit100); _w = ((int)(0 + _w + step100)) ) {
 //BA.debugLineNum = 206;BA.debugLine="existingregion = regions.Get(w)";
_existingregion = (b4a.example.region)(_regions.Get(_w));
 //BA.debugLineNum = 207;BA.debugLine="testregion = s.union(existingregion)";
_testregion = _s._union(_existingregion);
 //BA.debugLineNum = 208;BA.debugLine="If testregion.volume < s.volume + existi";
if (_testregion._volume()<_s._volume()+_existingregion._volume()) { 
 //BA.debugLineNum = 210;BA.debugLine="testregion.setSysCount(existingregion.S";
_testregion._setsyscount((int) (_existingregion._getsyscount()+1));
 //BA.debugLineNum = 211;BA.debugLine="regions.RemoveAt(w)";
_regions.RemoveAt(_w);
 //BA.debugLineNum = 212;BA.debugLine="regions.Add(testregion)";
_regions.Add((Object)(_testregion));
 //BA.debugLineNum = 213;BA.debugLine="ExistRegionFound = True";
_existregionfound = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 214;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 //BA.debugLineNum = 218;BA.debugLine="If ExistRegionFound = False Then";
if (_existregionfound==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 220;BA.debugLine="s.setSysCount(1)";
_s._setsyscount((int) (1));
 //BA.debugLineNum = 221;BA.debugLine="regions.Add(s)";
_regions.Add((Object)(_s));
 };
 //BA.debugLineNum = 224;BA.debugLine="systems.Add(combination)";
_systems.Add((Object)(_combination));
 };
 };
 }
};
 }
};
 }
};
 //BA.debugLineNum = 232;BA.debugLine="Dim bestcount = 0 , nextcount = 0 As Int";
_bestcount = (int) (0);
_nextcount = (int) (0);
 //BA.debugLineNum = 233;BA.debugLine="Dim bestlist, nextlist As List";
_bestlist = new anywheresoftware.b4a.objects.collections.List();
_nextlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 234;BA.debugLine="Dim testregion As Region";
_testregion = new b4a.example.region();
 //BA.debugLineNum = 235;BA.debugLine="Dim count, matches As Int";
_count = 0;
_matches = 0;
 //BA.debugLineNum = 236;BA.debugLine="Dim x, y, z As Double";
_x = 0;
_y = 0;
_z = 0;
 //BA.debugLineNum = 238;BA.debugLine="bestlist.Initialize";
_bestlist.Initialize();
 //BA.debugLineNum = 239;BA.debugLine="nextlist.Initialize";
_nextlist.Initialize();
 //BA.debugLineNum = 242;BA.debugLine="Dim highregions As List";
_highregions = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 243;BA.debugLine="highregions.Initialize";
_highregions.Initialize();
 //BA.debugLineNum = 245;BA.debugLine="For Each reg As Region In regions";
final anywheresoftware.b4a.BA.IterableList group131 = _regions;
final int groupLen131 = group131.getSize();
for (int index131 = 0;index131 < groupLen131 ;index131++){
_reg = (b4a.example.region)(group131.Get(index131));
 //BA.debugLineNum = 246;BA.debugLine="If reg.SysCount > 1 Then";
if (_reg._getsyscount()>1) { 
 //BA.debugLineNum = 247;BA.debugLine="highregions.Add(reg)";
_highregions.Add((Object)(_reg));
 };
 }
;
 //BA.debugLineNum = 252;BA.debugLine="For w = 0 To highregions.Size - 1";
{
final int step136 = 1;
final int limit136 = (int) (_highregions.getSize()-1);
for (_w = (int) (0) ; (step136 > 0 && _w <= limit136) || (step136 < 0 && _w >= limit136); _w = ((int)(0 + _w + step136)) ) {
 //BA.debugLineNum = 253;BA.debugLine="testregion = highregions.Get(w)";
_testregion = (b4a.example.region)(_highregions.Get(_w));
 //BA.debugLineNum = 254;BA.debugLine="For x = testregion.getminX To testregion.getma";
{
final double step138 = (1/(double)32.0);
final double limit138 = _testregion._getmaxx();
for (_x = _testregion._getminx() ; (step138 > 0 && _x <= limit138) || (step138 < 0 && _x >= limit138); _x = ((double)(0 + _x + step138)) ) {
 //BA.debugLineNum = 255;BA.debugLine="For y = testregion.getminY To testregion.getm";
{
final double step139 = (1/(double)32.0);
final double limit139 = _testregion._getmaxy();
for (_y = _testregion._getminy() ; (step139 > 0 && _y <= limit139) || (step139 < 0 && _y >= limit139); _y = ((double)(0 + _y + step139)) ) {
 //BA.debugLineNum = 256;BA.debugLine="For z = testregion.getminZ To testregion.get";
{
final double step140 = (1/(double)32.0);
final double limit140 = _testregion._getmaxz();
for (_z = _testregion._getminz() ; (step140 > 0 && _z <= limit140) || (step140 < 0 && _z >= limit140); _z = ((double)(0 + _z + step140)) ) {
 //BA.debugLineNum = 257;BA.debugLine="Log(\"x: \" & x & \", y: \" & y & \", z: \" & z &";
anywheresoftware.b4a.keywords.Common.Log("x: "+BA.NumberToString(_x)+", y: "+BA.NumberToString(_y)+", z: "+BA.NumberToString(_z)+" -- best: "+BA.NumberToString(_bestcount)+" next: "+BA.NumberToString(_nextcount));
 //BA.debugLineNum = 258;BA.debugLine="Dim p As Coordinate";
_p = new b4a.example.coordinate();
 //BA.debugLineNum = 259;BA.debugLine="p.Initialize(x, y, z)";
_p._initialize((_ba.processBA == null ? _ba : _ba.processBA),_x,_y,_z);
 //BA.debugLineNum = 260;BA.debugLine="count = 0";
_count = (int) (0);
 //BA.debugLineNum = 261;BA.debugLine="For cyc = 0 To i";
{
final int step145 = 1;
final int limit145 = _i;
for (_cyc = (int) (0) ; (step145 > 0 && _cyc <= limit145) || (step145 < 0 && _cyc >= limit145); _cyc = ((int)(0 + _cyc + step145)) ) {
 //BA.debugLineNum = 262;BA.debugLine="If coord(cyc).Dist = EDDist(p, coord(cyc),";
if (_coord[_cyc]._getdist()==_eddist(_ba,_p,_coord[_cyc],(int) (2))) { 
 //BA.debugLineNum = 263;BA.debugLine="count = count + 1";
_count = (int) (_count+1);
 };
 }
};
 //BA.debugLineNum = 266;BA.debugLine="matches = count";
_matches = _count;
 //BA.debugLineNum = 268;BA.debugLine="If matches > bestcount Then";
if (_matches>_bestcount) { 
 //BA.debugLineNum = 269;BA.debugLine="nextcount = bestcount";
_nextcount = _bestcount;
 //BA.debugLineNum = 270;BA.debugLine="nextlist.Clear";
_nextlist.Clear();
 //BA.debugLineNum = 271;BA.debugLine="nextlist.AddAll(bestlist)";
_nextlist.AddAll(_bestlist);
 //BA.debugLineNum = 272;BA.debugLine="bestcount = matches";
_bestcount = _matches;
 //BA.debugLineNum = 273;BA.debugLine="bestlist.Clear";
_bestlist.Clear();
 //BA.debugLineNum = 274;BA.debugLine="bestlist.Add(p)";
_bestlist.Add((Object)(_p));
 }else if(_matches==_bestcount) { 
 //BA.debugLineNum = 276;BA.debugLine="bestlist.Add(p)";
_bestlist.Add((Object)(_p));
 }else if(_matches>_nextcount) { 
 //BA.debugLineNum = 278;BA.debugLine="nextcount = matches";
_nextcount = _matches;
 //BA.debugLineNum = 279;BA.debugLine="nextlist.Clear";
_nextlist.Clear();
 //BA.debugLineNum = 280;BA.debugLine="nextlist.Add(p)";
_nextlist.Add((Object)(_p));
 }else if(_matches==_nextcount) { 
 //BA.debugLineNum = 282;BA.debugLine="nextlist.Add(p)";
_nextlist.Add((Object)(_p));
 };
 //BA.debugLineNum = 285;BA.debugLine="If matches > bestcount Then";
if (_matches>_bestcount) { 
 //BA.debugLineNum = 286;BA.debugLine="nextcount = bestcount";
_nextcount = _bestcount;
 //BA.debugLineNum = 287;BA.debugLine="nextlist.Clear";
_nextlist.Clear();
 //BA.debugLineNum = 288;BA.debugLine="nextlist.AddAll(bestlist)";
_nextlist.AddAll(_bestlist);
 //BA.debugLineNum = 289;BA.debugLine="bestcount = matches";
_bestcount = _matches;
 //BA.debugLineNum = 290;BA.debugLine="bestlist.Clear";
_bestlist.Clear();
 //BA.debugLineNum = 291;BA.debugLine="bestlist.Add(p)";
_bestlist.Add((Object)(_p));
 }else if(_matches==_bestcount) { 
 //BA.debugLineNum = 293;BA.debugLine="Dim found = False As Boolean";
_found = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 294;BA.debugLine="For Each coo As Coordinate In bestlist";
final anywheresoftware.b4a.BA.IterableList group176 = _bestlist;
final int groupLen176 = group176.getSize();
for (int index176 = 0;index176 < groupLen176 ;index176++){
_coo = (b4a.example.coordinate)(group176.Get(index176));
 //BA.debugLineNum = 295;BA.debugLine="If (coo.X = p.X And coo.Y = p.Y And coo.Z";
if ((_coo._getx()==_p._getx() && _coo._gety()==_p._gety() && _coo._getz()==_p._getz())) { 
 //BA.debugLineNum = 296;BA.debugLine="found = True";
_found = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 297;BA.debugLine="Exit";
if (true) break;
 };
 }
;
 //BA.debugLineNum = 300;BA.debugLine="If found = False Then";
if (_found==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 301;BA.debugLine="bestlist.Add(p)";
_bestlist.Add((Object)(_p));
 };
 }else if(_matches>_nextcount) { 
 //BA.debugLineNum = 304;BA.debugLine="nextcount = matches";
_nextcount = _matches;
 //BA.debugLineNum = 305;BA.debugLine="nextlist.Clear";
_nextlist.Clear();
 //BA.debugLineNum = 306;BA.debugLine="nextlist.Add(p)";
_nextlist.Add((Object)(_p));
 }else if(_matches==_nextcount) { 
 //BA.debugLineNum = 308;BA.debugLine="Dim found = False As Boolean";
_found = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 309;BA.debugLine="For Each coo As Coordinate In nextlist";
final anywheresoftware.b4a.BA.IterableList group191 = _nextlist;
final int groupLen191 = group191.getSize();
for (int index191 = 0;index191 < groupLen191 ;index191++){
_coo = (b4a.example.coordinate)(group191.Get(index191));
 //BA.debugLineNum = 310;BA.debugLine="If (coo.X = p.X And coo.Y = p.Y And coo.Z";
if ((_coo._getx()==_p._getx() && _coo._gety()==_p._gety() && _coo._getz()==_p._getz())) { 
 //BA.debugLineNum = 311;BA.debugLine="found = True";
_found = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 312;BA.debugLine="Exit";
if (true) break;
 };
 }
;
 //BA.debugLineNum = 315;BA.debugLine="If found = False Then";
if (_found==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 316;BA.debugLine="nextlist.Add(p)";
_nextlist.Add((Object)(_p));
 };
 };
 }
};
 }
};
 }
};
 }
};
 //BA.debugLineNum = 324;BA.debugLine="If bestcount >= 5 And (bestcount - nextcount) >";
if (_bestcount>=5 && (_bestcount-_nextcount)>=2) { 
 //BA.debugLineNum = 325;BA.debugLine="Dim bestcoord As Coordinate";
_bestcoord = new b4a.example.coordinate();
 //BA.debugLineNum = 326;BA.debugLine="bestcoord = bestlist.Get(0)";
_bestcoord = (b4a.example.coordinate)(_bestlist.Get((int) (0)));
 //BA.debugLineNum = 327;BA.debugLine="Return bestcoord";
if (true) return _bestcoord;
 };
 };
 }
};
 //BA.debugLineNum = 332;BA.debugLine="Return Null";
if (true) return (b4a.example.coordinate)(anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim m As Math";
_m = new b4a.example.math();
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static b4a.example.coordinate  _scalarprod(anywheresoftware.b4a.BA _ba,double _s,b4a.example.coordinate _v) throws Exception{
b4a.example.coordinate _result = null;
 //BA.debugLineNum = 359;BA.debugLine="Sub scalarProd(s As Double, v As Coordinate) As Co";
 //BA.debugLineNum = 360;BA.debugLine="Dim result As Coordinate";
_result = new b4a.example.coordinate();
 //BA.debugLineNum = 361;BA.debugLine="result.Initialize(s * v.X, s * v.Y, s * v.Z)";
_result._initialize((_ba.processBA == null ? _ba : _ba.processBA),_s*_v._getx(),_s*_v._gety(),_s*_v._getz());
 //BA.debugLineNum = 362;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return null;
}
public static b4a.example.coordinate  _sum(anywheresoftware.b4a.BA _ba,b4a.example.coordinate _p1,b4a.example.coordinate _p2) throws Exception{
b4a.example.coordinate _result = null;
 //BA.debugLineNum = 371;BA.debugLine="Sub sum(p1 As Coordinate, p2 As Coordinate) As Coo";
 //BA.debugLineNum = 372;BA.debugLine="Dim result As Coordinate";
_result = new b4a.example.coordinate();
 //BA.debugLineNum = 373;BA.debugLine="result.Initialize(p1.X + p2.X, p1.Y + p2.Y, p1.Z";
_result._initialize((_ba.processBA == null ? _ba : _ba.processBA),_p1._getx()+_p2._getx(),_p1._gety()+_p2._gety(),_p1._getz()+_p2._getz());
 //BA.debugLineNum = 374;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return null;
}
public static boolean  _systemexists(anywheresoftware.b4a.BA _ba,String _sysname) throws Exception{
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
boolean _result = false;
 //BA.debugLineNum = 602;BA.debugLine="Sub SystemExists(SysName As String) As Boolean";
 //BA.debugLineNum = 603;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 604;BA.debugLine="Dim CursSystems As Cursor";
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 605;BA.debugLine="Dim result As Boolean";
_result = false;
 //BA.debugLineNum = 608;BA.debugLine="Q = \"SELECT SystemName FROM Systems WHERE SystemN";
_q = "SELECT SystemName FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 609;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery2(Q, Array";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_q,new String[]{_sysname})));
 //BA.debugLineNum = 610;BA.debugLine="If CursSystems.RowCount > 0 Then";
if (_curssystems.getRowCount()>0) { 
 //BA.debugLineNum = 611;BA.debugLine="Msgbox(\"This Star System already exists\", \"A T T";
anywheresoftware.b4a.keywords.Common.Msgbox("This Star System already exists","A T T E N T I O N",_ba);
 //BA.debugLineNum = 612;BA.debugLine="result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 614;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 616;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 617;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 619;BA.debugLine="End Sub";
return false;
}
public static String  _updatesysly(anywheresoftware.b4a.BA _ba) throws Exception{
int _i = 0;
String _query = "";
double _result = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 12;BA.debugLine="Sub UpdateSysLY";
 //BA.debugLineNum = 13;BA.debugLine="Dim I As Int";
_i = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim Query As String";
_query = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim result As Double";
_result = 0;
 //BA.debugLineNum = 16;BA.debugLine="Dim CursSystems As Cursor";
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 19;BA.debugLine="ProgressDialogShow(\"Please wait your position in";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(_ba,"Please wait your position in the universe is being calculated...");
 //BA.debugLineNum = 22;BA.debugLine="Query = \"SELECT * FROM Systems ORDER BY SystemNam";
_query = "SELECT * FROM Systems ORDER BY SystemName ASC";
 //BA.debugLineNum = 23;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery(Query)";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_query)));
 //BA.debugLineNum = 24;BA.debugLine="If CursSystems.RowCount > 0 Then";
if (_curssystems.getRowCount()>0) { 
 //BA.debugLineNum = 25;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 26;BA.debugLine="For I = 0 To CursSystems.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_curssystems.getRowCount()-1);
for (_i = (int) (0) ; (step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11); _i = ((int)(0 + _i + step11)) ) {
 //BA.debugLineNum = 27;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 28;BA.debugLine="CursSystems.Position = I";
_curssystems.setPosition(_i);
 //BA.debugLineNum = 29;BA.debugLine="whereclause.Put(\"SystemName\",CursSystems.GetStr";
_whereclause.Put((Object)("SystemName"),(Object)(_curssystems.GetString("SystemName")));
 //BA.debugLineNum = 30;BA.debugLine="result = DistanceBetween(CursSystems.GetString(";
_result = _distancebetween(_ba,_curssystems.GetString("SystemName"),mostCurrent._starter._currlocation);
 //BA.debugLineNum = 31;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Systems\",";
mostCurrent._dbutils._updaterecord(_ba,mostCurrent._starter._sqlexec,"Systems","LYfromCurrent",(Object)(_result),_whereclause);
 }
};
 };
 //BA.debugLineNum = 34;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 36;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
}
