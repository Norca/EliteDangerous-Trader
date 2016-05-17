package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class math extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.math");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.math.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
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
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
public double  _doublemaxvalue() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Public Sub DoubleMaxValue As Double";
 //BA.debugLineNum = 16;BA.debugLine="Return 1.7976931348623157E+308";
if (true) return 1.7976931348623157e+308;
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return 0;
}
public double  _doubleminvalue() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Public Sub DoubleMinValue As Double";
 //BA.debugLineNum = 20;BA.debugLine="Return -1.7976931348623157E+308";
if (true) return -1.7976931348623157e+308;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 7;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public double  _pi() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Public Sub PI As Double";
 //BA.debugLineNum = 12;BA.debugLine="Return 3.1415926535897931";
if (true) return 3.1415926535897931;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return 0;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
