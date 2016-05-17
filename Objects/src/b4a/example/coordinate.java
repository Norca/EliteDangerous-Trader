package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class coordinate extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.coordinate");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.coordinate.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public double _coordx = 0;
public double _coordy = 0;
public double _coordz = 0;
public double _distance = 0;
public double _totalsqr = 0;
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
public b4a.example.elite _elite = null;
public b4a.example.commodupdate _commodupdate = null;
public b4a.example.edtables _edtables = null;
public b4a.example.anchordefine _anchordefine = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private coordx, coordy, coordz As Double";
_coordx = 0;
_coordy = 0;
_coordz = 0;
 //BA.debugLineNum = 4;BA.debugLine="Private distance As Double";
_distance = 0;
 //BA.debugLineNum = 5;BA.debugLine="Private totalsqr As Double";
_totalsqr = 0;
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public double  _getdist() throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Public Sub getDist As Double";
 //BA.debugLineNum = 42;BA.debugLine="Return distance";
if (true) return _distance;
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return 0;
}
public double  _gettotsqrerr() throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Public Sub getTotSqrErr As Double";
 //BA.debugLineNum = 50;BA.debugLine="Return totalsqr";
if (true) return _totalsqr;
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return 0;
}
public double  _getx() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Public Sub getX As Double";
 //BA.debugLineNum = 18;BA.debugLine="Return coordx";
if (true) return _coordx;
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return 0;
}
public double  _gety() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Public Sub getY As Double";
 //BA.debugLineNum = 22;BA.debugLine="Return coordy";
if (true) return _coordy;
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return 0;
}
public double  _getz() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Public Sub getZ As Double";
 //BA.debugLineNum = 26;BA.debugLine="Return coordz";
if (true) return _coordz;
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,double _x,double _y,double _z) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 9;BA.debugLine="Public Sub Initialize(x As Double, y As Double, z";
 //BA.debugLineNum = 10;BA.debugLine="coordx = x";
_coordx = _x;
 //BA.debugLineNum = 11;BA.debugLine="coordy = y";
_coordy = _y;
 //BA.debugLineNum = 12;BA.debugLine="coordz = z";
_coordz = _z;
 //BA.debugLineNum = 13;BA.debugLine="distance = 0";
_distance = 0;
 //BA.debugLineNum = 14;BA.debugLine="totalsqr = 0";
_totalsqr = 0;
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public String  _setdist(double _dist) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Public Sub setDist(dist As Double)";
 //BA.debugLineNum = 46;BA.debugLine="distance = dist";
_distance = _dist;
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public String  _settotsqrerr(double _tse) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Public Sub setTotSqrErr(tse As Double)";
 //BA.debugLineNum = 54;BA.debugLine="totalsqr = tse";
_totalsqr = _tse;
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public String  _setx(double _x) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Public Sub setX(x As Double)";
 //BA.debugLineNum = 30;BA.debugLine="coordx = x";
_coordx = _x;
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public String  _sety(double _y) throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Public Sub setY(y As Double)";
 //BA.debugLineNum = 34;BA.debugLine="coordy = y";
_coordy = _y;
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public String  _setz(double _z) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Public Sub setZ(z As Double)";
 //BA.debugLineNum = 38;BA.debugLine="coordz = z";
_coordz = _z;
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
