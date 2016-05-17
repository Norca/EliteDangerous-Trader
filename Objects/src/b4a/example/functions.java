package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class functions {
private static functions mostCurrent = new functions();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
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
public b4a.example.dbutils _dbutils = null;
public b4a.example.sqlutils _sqlutils = null;
public b4a.example.elite _elite = null;
public b4a.example.commodupdate _commodupdate = null;
public b4a.example.edtables _edtables = null;
public b4a.example.anchordefine _anchordefine = null;
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _autoeditbox(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _statedisabled = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _stateenabled = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _edbox = null;
 //BA.debugLineNum = 116;BA.debugLine="Sub AutoEditBox As StateListDrawable";
 //BA.debugLineNum = 118;BA.debugLine="Dim StateDisabled As ColorDrawable";
_statedisabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 119;BA.debugLine="StateDisabled.Initialize(Starter.AlphaColour2, 3)";
_statedisabled.Initialize(mostCurrent._starter._alphacolour2,(int) (3));
 //BA.debugLineNum = 121;BA.debugLine="Dim StateEnabled As ColorDrawable";
_stateenabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 122;BA.debugLine="StateEnabled.Initialize(Starter.BackColour1, 3)";
_stateenabled.Initialize(mostCurrent._starter._backcolour1,(int) (3));
 //BA.debugLineNum = 124;BA.debugLine="Dim EdBox As StateListDrawable";
_edbox = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 125;BA.debugLine="EdBox.Initialize";
_edbox.Initialize();
 //BA.debugLineNum = 126;BA.debugLine="EdBox.AddState(EdBox.State_Disabled, StateDisable";
_edbox.AddState(_edbox.State_Disabled,(android.graphics.drawable.Drawable)(_statedisabled.getObject()));
 //BA.debugLineNum = 127;BA.debugLine="EdBox.AddState(EdBox.State_Enabled, StateEnabled)";
_edbox.AddState(_edbox.State_Enabled,(android.graphics.drawable.Drawable)(_stateenabled.getObject()));
 //BA.debugLineNum = 128;BA.debugLine="Return EdBox";
if (true) return _edbox;
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return null;
}
public static int  _booltoint(anywheresoftware.b4a.BA _ba,boolean _check) throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub BoolToInt(Check As Boolean) As Int";
 //BA.debugLineNum = 72;BA.debugLine="If (Check) Then";
if ((_check)) { 
 //BA.debugLineNum = 73;BA.debugLine="Return 1";
if (true) return (int) (1);
 }else {
 //BA.debugLineNum = 75;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return 0;
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _editbox(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _statedisabled = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _stateenabled = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _edbox = null;
 //BA.debugLineNum = 132;BA.debugLine="Sub EditBox As StateListDrawable";
 //BA.debugLineNum = 134;BA.debugLine="Dim StateDisabled As ColorDrawable";
_statedisabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 135;BA.debugLine="StateDisabled.Initialize(Starter.AlphaColour2, 3)";
_statedisabled.Initialize(mostCurrent._starter._alphacolour2,(int) (3));
 //BA.debugLineNum = 137;BA.debugLine="Dim StateEnabled As ColorDrawable";
_stateenabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 138;BA.debugLine="StateEnabled.Initialize(Starter.BackColour1, 3)";
_stateenabled.Initialize(mostCurrent._starter._backcolour1,(int) (3));
 //BA.debugLineNum = 140;BA.debugLine="Dim EdBox As StateListDrawable";
_edbox = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 141;BA.debugLine="EdBox.Initialize";
_edbox.Initialize();
 //BA.debugLineNum = 142;BA.debugLine="EdBox.AddState(EdBox.State_Disabled, StateDisable";
_edbox.AddState(_edbox.State_Disabled,(android.graphics.drawable.Drawable)(_statedisabled.getObject()));
 //BA.debugLineNum = 143;BA.debugLine="EdBox.AddState(EdBox.State_Enabled, StateEnabled)";
_edbox.AddState(_edbox.State_Enabled,(android.graphics.drawable.Drawable)(_stateenabled.getObject()));
 //BA.debugLineNum = 144;BA.debugLine="Return EdBox";
if (true) return _edbox;
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return null;
}
public static int  _findcommodnum(anywheresoftware.b4a.BA _ba,String _commodname) throws Exception{
int _i = 0;
String _q = "";
int _result = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curscommod = null;
anywheresoftware.b4a.sql.SQL _sqlexec = null;
 //BA.debugLineNum = 35;BA.debugLine="Sub FindCommodNum(CommodName As String) As Int";
 //BA.debugLineNum = 36;BA.debugLine="Dim I As Int";
_i = 0;
 //BA.debugLineNum = 37;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 39;BA.debugLine="Dim CursCommod As Cursor";
_curscommod = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim SQLExec As SQL";
_sqlexec = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 42;BA.debugLine="SQLExec.Initialize(File.DirRootExternal, \"EliteTr";
_sqlexec.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 44;BA.debugLine="result = 0";
_result = (int) (0);
 //BA.debugLineNum = 45;BA.debugLine="Q = \"SELECT * FROM Commodities ORDER BY CommodDes";
_q = "SELECT * FROM Commodities ORDER BY CommodDesc ASC";
 //BA.debugLineNum = 46;BA.debugLine="CursCommod = SQLExec.ExecQuery(Q)";
_curscommod.setObject((android.database.Cursor)(_sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 47;BA.debugLine="If CursCommod.RowCount > 0 Then";
if (_curscommod.getRowCount()>0) { 
 //BA.debugLineNum = 48;BA.debugLine="For I = 0 To CursCommod.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_curscommod.getRowCount()-1);
for (_i = (int) (0) ; (step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11); _i = ((int)(0 + _i + step11)) ) {
 //BA.debugLineNum = 49;BA.debugLine="CursCommod.Position = I";
_curscommod.setPosition(_i);
 //BA.debugLineNum = 50;BA.debugLine="If CommodName = CursCommod.GetString(\"CommodDes";
if ((_commodname).equals(_curscommod.GetString("CommodDesc"))) { 
 //BA.debugLineNum = 51;BA.debugLine="result = CursCommod.GetInt(\"CommodID\")";
_result = _curscommod.GetInt("CommodID");
 //BA.debugLineNum = 52;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 //BA.debugLineNum = 56;BA.debugLine="CursCommod.Close";
_curscommod.Close();
 //BA.debugLineNum = 57;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return 0;
}
public static boolean  _inttobool(anywheresoftware.b4a.BA _ba,int _num) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub IntToBool(Num As Int) As Boolean";
 //BA.debugLineNum = 63;BA.debugLine="If Num = 1 Then";
if (_num==1) { 
 //BA.debugLineNum = 64;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 66;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _setcolours(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activ) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _viewobj = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.EditTextWrapper _edt = null;
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tog = null;
anywheresoftware.b4a.objects.SpinnerWrapper _spn = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cdborder = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper _act = null;
anywheresoftware.b4a.objects.WebViewWrapper _wbv = null;
 //BA.debugLineNum = 184;BA.debugLine="Sub SetColours(Activ As Activity)";
 //BA.debugLineNum = 186;BA.debugLine="For Each ViewObj As View In Activ.GetAllViewsRecu";
_viewobj = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
final anywheresoftware.b4a.BA.IterableList group1 = _activ.GetAllViewsRecursive();
final int groupLen1 = group1.getSize();
for (int index1 = 0;index1 < groupLen1 ;index1++){
_viewobj.setObject((android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 187;BA.debugLine="Log(GetType(ViewObj))";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.GetType((Object)(_viewobj.getObject())));
 //BA.debugLineNum = 188;BA.debugLine="Select GetType(ViewObj)";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.GetType((Object)(_viewobj.getObject())),"android.widget.TextView","android.widget.EditText","android.widget.Button","android.widget.ToggleButton","anywheresoftware.b4a.objects.SpinnerWrapper$B4ASpinner","anywheresoftware.b4a.BALayout","android.widget.AutoCompleteTextView","android.widget.TabWidget","android.webkit.WebView")) {
case 0: {
 //BA.debugLineNum = 190;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 191;BA.debugLine="lbl = ViewObj";
_lbl.setObject((android.widget.TextView)(_viewobj.getObject()));
 //BA.debugLineNum = 192;BA.debugLine="lbl .TextColor = Starter.TextColour";
_lbl.setTextColor(mostCurrent._starter._textcolour);
 break; }
case 1: {
 //BA.debugLineNum = 194;BA.debugLine="Dim edt As EditText";
_edt = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 195;BA.debugLine="edt = ViewObj";
_edt.setObject((android.widget.EditText)(_viewobj.getObject()));
 //BA.debugLineNum = 196;BA.debugLine="edt .Background = EditBox";
_edt.setBackground((android.graphics.drawable.Drawable)(_editbox(_ba).getObject()));
 break; }
case 2: {
 //BA.debugLineNum = 198;BA.debugLine="Dim btn As Button";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 199;BA.debugLine="btn = ViewObj";
_btn.setObject((android.widget.Button)(_viewobj.getObject()));
 //BA.debugLineNum = 200;BA.debugLine="btn .Background = StdButton";
_btn.setBackground((android.graphics.drawable.Drawable)(_stdbutton(_ba).getObject()));
 //BA.debugLineNum = 201;BA.debugLine="btn .TextColor = Starter.TextColour";
_btn.setTextColor(mostCurrent._starter._textcolour);
 break; }
case 3: {
 //BA.debugLineNum = 203;BA.debugLine="Dim tog As ToggleButton";
_tog = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 204;BA.debugLine="tog = ViewObj";
_tog.setObject((android.widget.ToggleButton)(_viewobj.getObject()));
 //BA.debugLineNum = 205;BA.debugLine="tog .Background = ToggleBut";
_tog.setBackground((android.graphics.drawable.Drawable)(_togglebut(_ba).getObject()));
 //BA.debugLineNum = 206;BA.debugLine="tog .TextColor = Starter.TextColour";
_tog.setTextColor(mostCurrent._starter._textcolour);
 break; }
case 4: {
 //BA.debugLineNum = 208;BA.debugLine="Dim spn As Spinner";
_spn = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 209;BA.debugLine="spn = ViewObj";
_spn.setObject((anywheresoftware.b4a.objects.SpinnerWrapper.B4ASpinner)(_viewobj.getObject()));
 //BA.debugLineNum = 210;BA.debugLine="spn .Background = Spin";
_spn.setBackground((android.graphics.drawable.Drawable)(_spin(_ba).getObject()));
 //BA.debugLineNum = 211;BA.debugLine="spn .DropdownBackgroundColor = Starter.AlphaCo";
_spn.setDropdownBackgroundColor(mostCurrent._starter._alphacolour2);
 //BA.debugLineNum = 212;BA.debugLine="spn .DropdownTextColor = Starter.TextColour";
_spn.setDropdownTextColor(mostCurrent._starter._textcolour);
 //BA.debugLineNum = 213;BA.debugLine="spn .TextColor = Starter.TextColour";
_spn.setTextColor(mostCurrent._starter._textcolour);
 break; }
case 5: {
 //BA.debugLineNum = 215;BA.debugLine="Dim cdBorder As ColorDrawable";
_cdborder = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 216;BA.debugLine="cdBorder.Initialize2(Colors.Black, 5, 3, Start";
_cdborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Black,(int) (5),(int) (3),mostCurrent._starter._bordercolour);
 //BA.debugLineNum = 217;BA.debugLine="Dim Pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 218;BA.debugLine="Pnl = ViewObj";
_pnl.setObject((android.view.ViewGroup)(_viewobj.getObject()));
 //BA.debugLineNum = 219;BA.debugLine="Pnl .Background = cdBorder";
_pnl.setBackground((android.graphics.drawable.Drawable)(_cdborder.getObject()));
 break; }
case 6: {
 //BA.debugLineNum = 221;BA.debugLine="Dim act As AutoCompleteEditText";
_act = new anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper();
 //BA.debugLineNum = 222;BA.debugLine="act = ViewObj";
_act.setObject((android.widget.EditText)(_viewobj.getObject()));
 //BA.debugLineNum = 223;BA.debugLine="act .Background = AutoEditBox";
_act.setBackground((android.graphics.drawable.Drawable)(_autoeditbox(_ba).getObject()));
 //BA.debugLineNum = 224;BA.debugLine="act .TextColor = Starter.TextColour";
_act.setTextColor(mostCurrent._starter._textcolour);
 break; }
case 7: {
 break; }
case 8: {
 //BA.debugLineNum = 227;BA.debugLine="Dim wbv As WebView";
_wbv = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 228;BA.debugLine="wbv = ViewObj";
_wbv.setObject((android.webkit.WebView)(_viewobj.getObject()));
 //BA.debugLineNum = 229;BA.debugLine="wbv .Color = Colors.Black";
_wbv.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 break; }
}
;
 }
;
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _spin(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _statedisabled = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _stateenabled = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _sp = null;
 //BA.debugLineNum = 100;BA.debugLine="Sub Spin As StateListDrawable";
 //BA.debugLineNum = 102;BA.debugLine="Dim StateDisabled As ColorDrawable";
_statedisabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 103;BA.debugLine="StateDisabled.Initialize(Starter.AlphaColour2, 3)";
_statedisabled.Initialize(mostCurrent._starter._alphacolour2,(int) (3));
 //BA.debugLineNum = 105;BA.debugLine="Dim StateEnabled As ColorDrawable";
_stateenabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 106;BA.debugLine="StateEnabled.Initialize2(Starter.AlphaColour1, 3,";
_stateenabled.Initialize2(mostCurrent._starter._alphacolour1,(int) (3),(int) (2),mostCurrent._starter._backcolour1);
 //BA.debugLineNum = 108;BA.debugLine="Dim Sp As StateListDrawable";
_sp = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 109;BA.debugLine="Sp.Initialize";
_sp.Initialize();
 //BA.debugLineNum = 110;BA.debugLine="Sp.AddState(Sp.State_Disabled, StateDisabled)";
_sp.AddState(_sp.State_Disabled,(android.graphics.drawable.Drawable)(_statedisabled.getObject()));
 //BA.debugLineNum = 111;BA.debugLine="Sp.AddState(Sp.State_Enabled, StateEnabled)";
_sp.AddState(_sp.State_Enabled,(android.graphics.drawable.Drawable)(_stateenabled.getObject()));
 //BA.debugLineNum = 112;BA.debugLine="Return Sp";
if (true) return _sp;
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return null;
}
public static boolean  _stationexists(anywheresoftware.b4a.BA _ba,String _statname) throws Exception{
String _query = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _curstempstations = null;
boolean _result = false;
anywheresoftware.b4a.sql.SQL _sqlexec = null;
 //BA.debugLineNum = 10;BA.debugLine="Sub StationExists(StatName As String) As Boolean";
 //BA.debugLineNum = 11;BA.debugLine="Dim Query As String";
_query = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim CursTempStations As Cursor";
_curstempstations = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim result As Boolean";
_result = false;
 //BA.debugLineNum = 14;BA.debugLine="Dim SQLExec As SQL";
_sqlexec = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 16;BA.debugLine="SQLExec.Initialize(File.DirRootExternal, \"EliteTr";
_sqlexec.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 21;BA.debugLine="Query = \"SELECT StationName FROM TempStations WHE";
_query = "SELECT StationName FROM TempStations WHERE StationName = ?";
 //BA.debugLineNum = 22;BA.debugLine="CursTempStations = Starter.SQLExec.ExecQuery2(Que";
_curstempstations.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_query,new String[]{_statname})));
 //BA.debugLineNum = 23;BA.debugLine="If CursTempStations.RowCount > 0 Then";
if (_curstempstations.getRowCount()>0) { 
 //BA.debugLineNum = 24;BA.debugLine="Msgbox(\"This Station already exists in the Star";
anywheresoftware.b4a.keywords.Common.Msgbox("This Station already exists in the Star System","A T T E N T I O N",_ba);
 //BA.debugLineNum = 25;BA.debugLine="result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 27;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 29;BA.debugLine="CursTempStations.Close";
_curstempstations.Close();
 //BA.debugLineNum = 30;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return false;
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _stdbutton(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _statedisabled = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _stateenabled = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _stdbut = null;
 //BA.debugLineNum = 148;BA.debugLine="Sub StdButton As StateListDrawable";
 //BA.debugLineNum = 150;BA.debugLine="Dim StateDisabled As ColorDrawable";
_statedisabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 151;BA.debugLine="StateDisabled.Initialize(Starter.AlphaColour2, 5)";
_statedisabled.Initialize(mostCurrent._starter._alphacolour2,(int) (5));
 //BA.debugLineNum = 153;BA.debugLine="Dim StateEnabled As ColorDrawable";
_stateenabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 154;BA.debugLine="StateEnabled.Initialize(Starter.BackColour1, 5)";
_stateenabled.Initialize(mostCurrent._starter._backcolour1,(int) (5));
 //BA.debugLineNum = 156;BA.debugLine="Dim StdBut As StateListDrawable";
_stdbut = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 157;BA.debugLine="StdBut.Initialize";
_stdbut.Initialize();
 //BA.debugLineNum = 158;BA.debugLine="StdBut.AddState(StdBut.State_Disabled, StateDisab";
_stdbut.AddState(_stdbut.State_Disabled,(android.graphics.drawable.Drawable)(_statedisabled.getObject()));
 //BA.debugLineNum = 159;BA.debugLine="StdBut.AddState(StdBut.State_Enabled, StateEnable";
_stdbut.AddState(_stdbut.State_Enabled,(android.graphics.drawable.Drawable)(_stateenabled.getObject()));
 //BA.debugLineNum = 160;BA.debugLine="Return StdBut";
if (true) return _stdbut;
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _togglebut(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _statedisabled = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _statechecked = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _stateunchecked = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _togbut = null;
 //BA.debugLineNum = 80;BA.debugLine="Sub ToggleBut As StateListDrawable";
 //BA.debugLineNum = 82;BA.debugLine="Dim StateDisabled As ColorDrawable";
_statedisabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 83;BA.debugLine="StateDisabled.Initialize(Starter.AlphaColour2, 5)";
_statedisabled.Initialize(mostCurrent._starter._alphacolour2,(int) (5));
 //BA.debugLineNum = 85;BA.debugLine="Dim StateChecked As ColorDrawable";
_statechecked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 86;BA.debugLine="StateChecked.Initialize2(Starter.BackColour2, 5,";
_statechecked.Initialize2(mostCurrent._starter._backcolour2,(int) (5),(int) (3),mostCurrent._starter._highlightcolour);
 //BA.debugLineNum = 88;BA.debugLine="Dim StateUnchecked As ColorDrawable";
_stateunchecked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 89;BA.debugLine="StateUnchecked.Initialize(Starter.BackColour1, 5)";
_stateunchecked.Initialize(mostCurrent._starter._backcolour1,(int) (5));
 //BA.debugLineNum = 91;BA.debugLine="Dim TogBut As StateListDrawable";
_togbut = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 92;BA.debugLine="TogBut.Initialize";
_togbut.Initialize();
 //BA.debugLineNum = 93;BA.debugLine="TogBut.AddState(TogBut.State_Disabled, StateDisab";
_togbut.AddState(_togbut.State_Disabled,(android.graphics.drawable.Drawable)(_statedisabled.getObject()));
 //BA.debugLineNum = 94;BA.debugLine="TogBut.AddState(TogBut.State_Checked, StateChecke";
_togbut.AddState(_togbut.State_Checked,(android.graphics.drawable.Drawable)(_statechecked.getObject()));
 //BA.debugLineNum = 95;BA.debugLine="TogBut.AddState(TogBut.State_Unchecked, StateUnch";
_togbut.AddState(_togbut.State_Unchecked,(android.graphics.drawable.Drawable)(_stateunchecked.getObject()));
 //BA.debugLineNum = 96;BA.debugLine="Return TogBut";
if (true) return _togbut;
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _togglebutgood(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _statedisabled = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _statechecked = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _stateunchecked = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _togbut = null;
 //BA.debugLineNum = 164;BA.debugLine="Sub ToggleButGood As StateListDrawable";
 //BA.debugLineNum = 166;BA.debugLine="Dim StateDisabled As ColorDrawable";
_statedisabled = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 167;BA.debugLine="StateDisabled.Initialize(Starter.AlphaColour1, 5)";
_statedisabled.Initialize(mostCurrent._starter._alphacolour1,(int) (5));
 //BA.debugLineNum = 169;BA.debugLine="Dim StateChecked As ColorDrawable";
_statechecked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 170;BA.debugLine="StateChecked.Initialize2(Starter.BackColour2, 5,";
_statechecked.Initialize2(mostCurrent._starter._backcolour2,(int) (5),(int) (3),mostCurrent._starter._highlightcolour);
 //BA.debugLineNum = 172;BA.debugLine="Dim StateUnchecked As ColorDrawable";
_stateunchecked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 173;BA.debugLine="StateUnchecked.Initialize(Starter.BackColour1, 5)";
_stateunchecked.Initialize(mostCurrent._starter._backcolour1,(int) (5));
 //BA.debugLineNum = 175;BA.debugLine="Dim TogBut As StateListDrawable";
_togbut = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 176;BA.debugLine="TogBut.Initialize";
_togbut.Initialize();
 //BA.debugLineNum = 177;BA.debugLine="TogBut.AddState(TogBut.State_Disabled, StateDisab";
_togbut.AddState(_togbut.State_Disabled,(android.graphics.drawable.Drawable)(_statedisabled.getObject()));
 //BA.debugLineNum = 178;BA.debugLine="TogBut.AddState(TogBut.State_Checked, StateChecke";
_togbut.AddState(_togbut.State_Checked,(android.graphics.drawable.Drawable)(_statechecked.getObject()));
 //BA.debugLineNum = 179;BA.debugLine="TogBut.AddState(TogBut.State_Unchecked, StateUnch";
_togbut.AddState(_togbut.State_Unchecked,(android.graphics.drawable.Drawable)(_stateunchecked.getObject()));
 //BA.debugLineNum = 180;BA.debugLine="Return TogBut";
if (true) return _togbut;
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return null;
}
}
