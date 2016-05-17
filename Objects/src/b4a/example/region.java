package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class region extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.region");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.region.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public double _minx = 0;
public double _maxx = 0;
public double _miny = 0;
public double _maxy = 0;
public double _minz = 0;
public double _maxz = 0;
public int _systemcounter = 0;
public double _centerx = 0;
public double _centery = 0;
public double _centerz = 0;
public int _regionsize = 0;
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
 //BA.debugLineNum = 3;BA.debugLine="Private minX, maxX, minY, maxY, minZ, maxZ As Dou";
_minx = 0;
_maxx = 0;
_miny = 0;
_maxy = 0;
_minz = 0;
_maxz = 0;
 //BA.debugLineNum = 4;BA.debugLine="Public systemcounter As Int";
_systemcounter = 0;
 //BA.debugLineNum = 5;BA.debugLine="Private centerX, centerY, centerZ As Double";
_centerx = 0;
_centery = 0;
_centerz = 0;
 //BA.debugLineNum = 6;BA.debugLine="Private regionSize As Int";
_regionsize = 0;
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public boolean  _contained(b4a.example.coordinate _coord) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Public Sub contained(coord As Coordinate) As Boole";
 //BA.debugLineNum = 91;BA.debugLine="Return (coord.X >= minX And coord.X <= maxX And c";
if (true) return (_coord._getx()>=_minx && _coord._getx()<=_maxx && _coord._gety()>=_miny && _coord._gety()<=_maxy && _coord._getz()>=_minz && _coord._getz()<=_maxz);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return false;
}
public b4a.example.coordinate  _getcenter() throws Exception{
b4a.example.coordinate _center = null;
 //BA.debugLineNum = 81;BA.debugLine="Public Sub getCenter As Coordinate";
 //BA.debugLineNum = 82;BA.debugLine="Dim center As Coordinate";
_center = new b4a.example.coordinate();
 //BA.debugLineNum = 83;BA.debugLine="centerX = ((maxX - minX) / 2) + minX";
_centerx = ((_maxx-_minx)/(double)2)+_minx;
 //BA.debugLineNum = 84;BA.debugLine="centerY = ((maxY - minY) / 2) + minY";
_centery = ((_maxy-_miny)/(double)2)+_miny;
 //BA.debugLineNum = 85;BA.debugLine="centerZ = ((maxZ - minZ) / 2) + minZ";
_centerz = ((_maxz-_minz)/(double)2)+_minz;
 //BA.debugLineNum = 86;BA.debugLine="center.Initialize(centerX, centerY, centerZ)";
_center._initialize(ba,_centerx,_centery,_centerz);
 //BA.debugLineNum = 87;BA.debugLine="Return center";
if (true) return _center;
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return null;
}
public double  _getmaxx() throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Public Sub getmaxX As Double";
 //BA.debugLineNum = 37;BA.debugLine="Return maxX";
if (true) return _maxx;
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return 0;
}
public double  _getmaxy() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Public Sub getmaxY As Double";
 //BA.debugLineNum = 41;BA.debugLine="Return maxY";
if (true) return _maxy;
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return 0;
}
public double  _getmaxz() throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Public Sub getmaxZ As Double";
 //BA.debugLineNum = 45;BA.debugLine="Return maxZ";
if (true) return _maxz;
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return 0;
}
public double  _getminx() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Public Sub getminX As Double";
 //BA.debugLineNum = 25;BA.debugLine="Return minX";
if (true) return _minx;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return 0;
}
public double  _getminy() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Public Sub getminY As Double";
 //BA.debugLineNum = 29;BA.debugLine="Return minY";
if (true) return _miny;
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return 0;
}
public double  _getminz() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Public Sub getminZ As Double";
 //BA.debugLineNum = 33;BA.debugLine="Return minZ";
if (true) return _minz;
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return 0;
}
public int  _getsyscount() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Public Sub getSysCount As Int";
 //BA.debugLineNum = 95;BA.debugLine="Return systemcounter";
if (true) return _systemcounter;
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 10;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 11;BA.debugLine="minX = 0";
_minx = 0;
 //BA.debugLineNum = 12;BA.debugLine="maxX = 0";
_maxx = 0;
 //BA.debugLineNum = 13;BA.debugLine="minY = 0";
_miny = 0;
 //BA.debugLineNum = 14;BA.debugLine="maxY = 0";
_maxy = 0;
 //BA.debugLineNum = 15;BA.debugLine="minZ = 0";
_minz = 0;
 //BA.debugLineNum = 16;BA.debugLine="maxZ = 0";
_maxz = 0;
 //BA.debugLineNum = 17;BA.debugLine="centerX = 0";
_centerx = 0;
 //BA.debugLineNum = 18;BA.debugLine="centerY = 0";
_centery = 0;
 //BA.debugLineNum = 19;BA.debugLine="centerZ = 0";
_centerz = 0;
 //BA.debugLineNum = 20;BA.debugLine="systemcounter = 0";
_systemcounter = (int) (0);
 //BA.debugLineNum = 21;BA.debugLine="regionSize = 1";
_regionsize = (int) (1);
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public String  _setall(b4a.example.coordinate _coord) throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Public Sub setAll(coord As Coordinate)";
 //BA.debugLineNum = 73;BA.debugLine="setminX(coord.X)";
_setminx(_coord._getx());
 //BA.debugLineNum = 74;BA.debugLine="setmaxX(coord.X)";
_setmaxx(_coord._getx());
 //BA.debugLineNum = 75;BA.debugLine="setminY(coord.Y)";
_setminy(_coord._gety());
 //BA.debugLineNum = 76;BA.debugLine="setmaxY(coord.Y)";
_setmaxy(_coord._gety());
 //BA.debugLineNum = 77;BA.debugLine="setminZ(coord.Z)";
_setminz(_coord._getz());
 //BA.debugLineNum = 78;BA.debugLine="setmaxZ(coord.Z)";
_setmaxz(_coord._getz());
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public String  _setmaxx(double _xcenter) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Private Sub setmaxX(XCenter As Double)";
 //BA.debugLineNum = 53;BA.debugLine="maxX = Ceil(XCenter * 32 + regionSize) /32";
_maxx = __c.Ceil(_xcenter*32+_regionsize)/(double)32;
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public String  _setmaxy(double _ycenter) throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Private Sub setmaxY(YCenter As Double)";
 //BA.debugLineNum = 61;BA.debugLine="maxY = Ceil(YCenter * 32 + regionSize) / 32";
_maxy = __c.Ceil(_ycenter*32+_regionsize)/(double)32;
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public String  _setmaxz(double _zcenter) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Private Sub setmaxZ(ZCenter As Double)";
 //BA.debugLineNum = 69;BA.debugLine="maxZ = Ceil(ZCenter * 32 + regionSize) / 32";
_maxz = __c.Ceil(_zcenter*32+_regionsize)/(double)32;
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public String  _setminx(double _xcenter) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Private Sub setminX(XCenter As Double)";
 //BA.debugLineNum = 49;BA.debugLine="minX = Floor(XCenter * 32 - regionSize) / 32";
_minx = __c.Floor(_xcenter*32-_regionsize)/(double)32;
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public String  _setminy(double _ycenter) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Private Sub setminY(YCenter As Double)";
 //BA.debugLineNum = 57;BA.debugLine="minY = Floor(YCenter * 32 - regionSize) / 32";
_miny = __c.Floor(_ycenter*32-_regionsize)/(double)32;
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public String  _setminz(double _zcenter) throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Private Sub setminZ(ZCenter As Double)";
 //BA.debugLineNum = 65;BA.debugLine="minZ = Floor(ZCenter * 32 - regionSize) / 32";
_minz = __c.Floor(_zcenter*32-_regionsize)/(double)32;
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public String  _setsyscount(int _count) throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Public Sub setSysCount(count As Int)";
 //BA.debugLineNum = 99;BA.debugLine="systemcounter = count";
_systemcounter = _count;
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public b4a.example.region  _union(b4a.example.region _r) throws Exception{
b4a.example.region _u = null;
 //BA.debugLineNum = 106;BA.debugLine="Public Sub union(r As Region) As Region";
 //BA.debugLineNum = 107;BA.debugLine="Dim u As Region";
_u = new b4a.example.region();
 //BA.debugLineNum = 108;BA.debugLine="u.Initialize";
_u._initialize(ba);
 //BA.debugLineNum = 110;BA.debugLine="u.minX = Min(minX, r.minX)";
_u._minx = __c.Min(_minx,_r._minx);
 //BA.debugLineNum = 111;BA.debugLine="u.minY = Min(minY, r.minY)";
_u._miny = __c.Min(_miny,_r._miny);
 //BA.debugLineNum = 112;BA.debugLine="u.minZ = Min(minZ, r.minZ)";
_u._minz = __c.Min(_minz,_r._minz);
 //BA.debugLineNum = 113;BA.debugLine="u.maxX = Max(maxX, r.maxX)";
_u._maxx = __c.Max(_maxx,_r._maxx);
 //BA.debugLineNum = 114;BA.debugLine="u.maxY = Max(maxY, r.maxY)";
_u._maxy = __c.Max(_maxy,_r._maxy);
 //BA.debugLineNum = 115;BA.debugLine="u.maxZ = Max(maxZ, r.maxZ)";
_u._maxz = __c.Max(_maxz,_r._maxz);
 //BA.debugLineNum = 117;BA.debugLine="Return u";
if (true) return _u;
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return null;
}
public double  _volume() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Public Sub volume As Double";
 //BA.debugLineNum = 103;BA.debugLine="Return (32768 * (maxX - minX + 1 / 32) * (maxY -";
if (true) return (32768*(_maxx-_minx+1/(double)32)*(_maxy-_miny+1/(double)32)*(_maxz-_minz+1/(double)32));
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return 0;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
