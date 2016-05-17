package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class stockmarketmaint extends Activity implements B4AActivity{
	public static stockmarketmaint mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.stockmarketmaint");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (stockmarketmaint).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.stockmarketmaint");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.stockmarketmaint", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (stockmarketmaint) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (stockmarketmaint) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return stockmarketmaint.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (stockmarketmaint) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (stockmarketmaint) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfirst = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprevsect = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnextsect = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlast = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcommod = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcommodgroup = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomgrp = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldemand = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsupply = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnstation = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnna = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbndemhigh = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbndemmed = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbndemlow = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnsuphigh = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnsupmed = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnsuplow = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvstocklist = null;
public anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper _acetsystemname = null;
public static String _systemname = "";
public static String _stationname = "";
public static int _smlmax = 0;
public static int _smlposition = 0;
public static int _smitem = 0;
public static String _loadedtype = "";
public static int _loadedlvl = 0;
public static boolean _itemchanged = false;
public anywheresoftware.b4a.objects.collections.List _systemlocation = null;
public anywheresoftware.b4a.objects.collections.List _stationmarketlist = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnldemand = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsupply = null;
public static String _q = "";
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.dbadmin _dbadmin = null;
public b4a.example.galaxymaint _galaxymaint = null;
public b4a.example.systemsmaint _systemsmaint = null;
public b4a.example.stationsmaint _stationsmaint = null;
public b4a.example.splash _splash = null;
public b4a.example.functions _functions = null;
public b4a.example.dbutils _dbutils = null;
public b4a.example.sqlutils _sqlutils = null;
public b4a.example.elite _elite = null;
public b4a.example.commodupdate _commodupdate = null;
public b4a.example.edtables _edtables = null;
public b4a.example.anchordefine _anchordefine = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _acetsystemname_enterpressed() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstation = null;
 //BA.debugLineNum = 594;BA.debugLine="Sub acetSystemName_EnterPressed";
 //BA.debugLineNum = 595;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 596;BA.debugLine="Dim CursStation As Cursor";
_cursstation = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 599;BA.debugLine="Q = \"SELECT * FROM Stations WHERE SystemName = ?";
mostCurrent._q = "SELECT * FROM Stations WHERE SystemName = ? AND EconomyNum > 0 ORDER BY StationName ASC";
 //BA.debugLineNum = 600;BA.debugLine="CursStation = Starter.SQLExec.ExecQuery2(Q, Array";
_cursstation.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 601;BA.debugLine="If CursStation.RowCount > 0 Then";
if (_cursstation.getRowCount()>0) { 
 //BA.debugLineNum = 602;BA.debugLine="spnStation.Clear";
mostCurrent._spnstation.Clear();
 //BA.debugLineNum = 603;BA.debugLine="spnStation.Add(\"Not Set\")";
mostCurrent._spnstation.Add("Not Set");
 //BA.debugLineNum = 604;BA.debugLine="For i = 0 To CursStation.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (_cursstation.getRowCount()-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 605;BA.debugLine="CursStation.Position = i";
_cursstation.setPosition(_i);
 //BA.debugLineNum = 606;BA.debugLine="spnStation.Add(CursStation.GetString(\"StationNa";
mostCurrent._spnstation.Add(_cursstation.GetString("StationName"));
 }
};
 };
 //BA.debugLineNum = 609;BA.debugLine="spnStation.SelectedIndex = 0";
mostCurrent._spnstation.setSelectedIndex((int) (0));
 //BA.debugLineNum = 610;BA.debugLine="CursStation.Close";
_cursstation.Close();
 //BA.debugLineNum = 612;BA.debugLine="End Sub";
return "";
}
public static String  _acetsystemname_itemclick(String _value) throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstation = null;
 //BA.debugLineNum = 574;BA.debugLine="Sub acetSystemName_ItemClick (Value As String)";
 //BA.debugLineNum = 575;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 576;BA.debugLine="Dim CursStation As Cursor";
_cursstation = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 579;BA.debugLine="Q = \"SELECT * FROM Stations WHERE SystemName = ?";
mostCurrent._q = "SELECT * FROM Stations WHERE SystemName = ? AND EconomyNum > 0 ORDER BY StationName ASC";
 //BA.debugLineNum = 580;BA.debugLine="CursStation = Starter.SQLExec.ExecQuery2(Q, Array";
_cursstation.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 581;BA.debugLine="If CursStation.RowCount > 0 Then";
if (_cursstation.getRowCount()>0) { 
 //BA.debugLineNum = 582;BA.debugLine="spnStation.Clear";
mostCurrent._spnstation.Clear();
 //BA.debugLineNum = 583;BA.debugLine="spnStation.Add(\"Not Set\")";
mostCurrent._spnstation.Add("Not Set");
 //BA.debugLineNum = 584;BA.debugLine="For i = 0 To CursStation.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (_cursstation.getRowCount()-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 585;BA.debugLine="CursStation.Position = i";
_cursstation.setPosition(_i);
 //BA.debugLineNum = 586;BA.debugLine="spnStation.Add(CursStation.GetString(\"StationNa";
mostCurrent._spnstation.Add(_cursstation.GetString("StationName"));
 }
};
 };
 //BA.debugLineNum = 589;BA.debugLine="spnStation.SelectedIndex = 0";
mostCurrent._spnstation.setSelectedIndex((int) (0));
 //BA.debugLineNum = 590;BA.debugLine="CursStation.Close";
_cursstation.Close();
 //BA.debugLineNum = 592;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 65;BA.debugLine="Activity.LoadLayout(\"StockMarket\")";
mostCurrent._activity.LoadLayout("StockMarket",mostCurrent.activityBA);
 //BA.debugLineNum = 67;BA.debugLine="LoadSystemLocation";
_loadsystemlocation();
 //BA.debugLineNum = 68;BA.debugLine="InitSystemLocation";
_initsystemlocation();
 //BA.debugLineNum = 69;BA.debugLine="SMLPosition = 0";
_smlposition = (int) (0);
 //BA.debugLineNum = 70;BA.debugLine="lblCommodGroup.Text = \"\"";
mostCurrent._lblcommodgroup.setText((Object)(""));
 //BA.debugLineNum = 71;BA.debugLine="lblComGrp.Text = \"\"";
mostCurrent._lblcomgrp.setText((Object)(""));
 //BA.debugLineNum = 73;BA.debugLine="Functions.SetColours(Activity)";
mostCurrent._functions._setcolours(mostCurrent.activityBA,mostCurrent._activity);
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _btnexit_click() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub btnExit_Click";
 //BA.debugLineNum = 208;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _btnfirst_click() throws Exception{
 //BA.debugLineNum = 151;BA.debugLine="Sub btnFirst_Click";
 //BA.debugLineNum = 152;BA.debugLine="SMLPosition = 0";
_smlposition = (int) (0);
 //BA.debugLineNum = 153;BA.debugLine="FillMarketList";
_fillmarketlist();
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _btnlast_click() throws Exception{
 //BA.debugLineNum = 156;BA.debugLine="Sub btnLast_Click";
 //BA.debugLineNum = 157;BA.debugLine="SMLPosition = SMLMax";
_smlposition = _smlmax;
 //BA.debugLineNum = 158;BA.debugLine="FillMarketList";
_fillmarketlist();
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub btnNext_Click";
 //BA.debugLineNum = 145;BA.debugLine="If SMLPosition < SMLMax Then";
if (_smlposition<_smlmax) { 
 //BA.debugLineNum = 146;BA.debugLine="SMLPosition = SMLPosition + 1";
_smlposition = (int) (_smlposition+1);
 //BA.debugLineNum = 147;BA.debugLine="FillMarketList";
_fillmarketlist();
 };
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _btnnextsect_click() throws Exception{
String[] _val = null;
String _currcommodgroup = "";
String _newcommodgroup = "";
boolean _changed = false;
 //BA.debugLineNum = 184;BA.debugLine="Sub btnNextSect_Click";
 //BA.debugLineNum = 185;BA.debugLine="Dim val(5) As String";
_val = new String[(int) (5)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 186;BA.debugLine="Dim currCommodGroup As String";
_currcommodgroup = "";
 //BA.debugLineNum = 187;BA.debugLine="Dim newCommodGroup As String";
_newcommodgroup = "";
 //BA.debugLineNum = 188;BA.debugLine="Dim changed = False As Boolean";
_changed = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 189;BA.debugLine="If SMLPosition < SMLMax Then";
if (_smlposition<_smlmax) { 
 //BA.debugLineNum = 190;BA.debugLine="Do While changed = False";
while (_changed==anywheresoftware.b4a.keywords.Common.False) {
 //BA.debugLineNum = 191;BA.debugLine="val = StationMarketList.Get(SMLPosition)";
_val = (String[])(mostCurrent._stationmarketlist.Get(_smlposition));
 //BA.debugLineNum = 192;BA.debugLine="currCommodGroup = val(3).ToUpperCase";
_currcommodgroup = _val[(int) (3)].toUpperCase();
 //BA.debugLineNum = 193;BA.debugLine="SMLPosition = SMLPosition + 1";
_smlposition = (int) (_smlposition+1);
 //BA.debugLineNum = 194;BA.debugLine="If SMLPosition = SMLMax Then";
if (_smlposition==_smlmax) { 
 //BA.debugLineNum = 195;BA.debugLine="changed = True";
_changed = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 197;BA.debugLine="val = StationMarketList.Get(SMLPosition)";
_val = (String[])(mostCurrent._stationmarketlist.Get(_smlposition));
 //BA.debugLineNum = 198;BA.debugLine="newCommodGroup = val(3).ToUpperCase";
_newcommodgroup = _val[(int) (3)].toUpperCase();
 //BA.debugLineNum = 199;BA.debugLine="If newCommodGroup <> currCommodGroup Then";
if ((_newcommodgroup).equals(_currcommodgroup) == false) { 
 //BA.debugLineNum = 200;BA.debugLine="changed = True";
_changed = anywheresoftware.b4a.keywords.Common.True;
 };
 }
;
 };
 //BA.debugLineNum = 204;BA.debugLine="FillMarketList";
_fillmarketlist();
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 137;BA.debugLine="Sub btnPrev_Click";
 //BA.debugLineNum = 138;BA.debugLine="If SMLPosition > 0 Then";
if (_smlposition>0) { 
 //BA.debugLineNum = 139;BA.debugLine="SMLPosition = SMLPosition - 1";
_smlposition = (int) (_smlposition-1);
 //BA.debugLineNum = 140;BA.debugLine="FillMarketList";
_fillmarketlist();
 };
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _btnprevsect_click() throws Exception{
String[] _val = null;
String _currcommodgroup = "";
String _newcommodgroup = "";
boolean _changed = false;
 //BA.debugLineNum = 161;BA.debugLine="Sub btnPrevSect_Click";
 //BA.debugLineNum = 162;BA.debugLine="Dim val(5) As String";
_val = new String[(int) (5)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 163;BA.debugLine="Dim currCommodGroup As String";
_currcommodgroup = "";
 //BA.debugLineNum = 164;BA.debugLine="Dim newCommodGroup As String";
_newcommodgroup = "";
 //BA.debugLineNum = 165;BA.debugLine="Dim changed = False As Boolean";
_changed = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 166;BA.debugLine="If SMLPosition > 0 Then";
if (_smlposition>0) { 
 //BA.debugLineNum = 167;BA.debugLine="Do While changed = False";
while (_changed==anywheresoftware.b4a.keywords.Common.False) {
 //BA.debugLineNum = 168;BA.debugLine="val = StationMarketList.Get(SMLPosition)";
_val = (String[])(mostCurrent._stationmarketlist.Get(_smlposition));
 //BA.debugLineNum = 169;BA.debugLine="currCommodGroup = val(3).ToUpperCase";
_currcommodgroup = _val[(int) (3)].toUpperCase();
 //BA.debugLineNum = 170;BA.debugLine="SMLPosition = SMLPosition - 1";
_smlposition = (int) (_smlposition-1);
 //BA.debugLineNum = 171;BA.debugLine="If SMLPosition = 0 Then";
if (_smlposition==0) { 
 //BA.debugLineNum = 172;BA.debugLine="changed = True";
_changed = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 174;BA.debugLine="val = StationMarketList.Get(SMLPosition)";
_val = (String[])(mostCurrent._stationmarketlist.Get(_smlposition));
 //BA.debugLineNum = 175;BA.debugLine="newCommodGroup = val(3).ToUpperCase";
_newcommodgroup = _val[(int) (3)].toUpperCase();
 //BA.debugLineNum = 176;BA.debugLine="If newCommodGroup <> currCommodGroup Then";
if ((_newcommodgroup).equals(_currcommodgroup) == false) { 
 //BA.debugLineNum = 177;BA.debugLine="changed = True";
_changed = anywheresoftware.b4a.keywords.Common.True;
 };
 }
;
 };
 //BA.debugLineNum = 181;BA.debugLine="FillMarketList";
_fillmarketlist();
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public static String  _displayedititem(int _smlnum) throws Exception{
String[] _val = null;
 //BA.debugLineNum = 348;BA.debugLine="Sub DisplayEditItem(SMLNum As Int)";
 //BA.debugLineNum = 349;BA.debugLine="Dim Val(5) As String";
_val = new String[(int) (5)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 351;BA.debugLine="Val = StationMarketList.Get(SMLNum)";
_val = (String[])(mostCurrent._stationmarketlist.Get(_smlnum));
 //BA.debugLineNum = 353;BA.debugLine="SMItem = Val(0)";
_smitem = (int)(Double.parseDouble(_val[(int) (0)]));
 //BA.debugLineNum = 354;BA.debugLine="lblComGrp.Text = Val(3).ToUpperCase";
mostCurrent._lblcomgrp.setText((Object)(_val[(int) (3)].toUpperCase()));
 //BA.debugLineNum = 355;BA.debugLine="lblCommodGroup.Text = Val(3)";
mostCurrent._lblcommodgroup.setText((Object)(_val[(int) (3)]));
 //BA.debugLineNum = 356;BA.debugLine="lblCommod.Text = Val(4).ToUpperCase";
mostCurrent._lblcommod.setText((Object)(_val[(int) (4)].toUpperCase()));
 //BA.debugLineNum = 357;BA.debugLine="LoadedType = Val(1)";
mostCurrent._loadedtype = _val[(int) (1)];
 //BA.debugLineNum = 358;BA.debugLine="LoadedLvl = Val(2)";
_loadedlvl = (int)(Double.parseDouble(_val[(int) (2)]));
 //BA.debugLineNum = 360;BA.debugLine="Select True";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.True,(_val[(int) (1)]).equals("Not Tradeable"),(_val[(int) (1)]).equals("Demand"),(_val[(int) (1)]).equals("Supply"),(_val[(int) (1)]).equals(""))) {
case 0: {
 //BA.debugLineNum = 362;BA.debugLine="tbnNA.Checked = True";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 364;BA.debugLine="Select True";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.True,(_val[(int) (2)]).equals(BA.NumberToString(4)),(_val[(int) (2)]).equals(BA.NumberToString(2)),(_val[(int) (2)]).equals(BA.NumberToString(1)))) {
case 0: {
 //BA.debugLineNum = 366;BA.debugLine="tbnDemHigh.Checked = True";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 368;BA.debugLine="tbnDemMed.Checked = True";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 2: {
 //BA.debugLineNum = 370;BA.debugLine="tbnDemLow.Checked = True";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
}
;
 break; }
case 2: {
 //BA.debugLineNum = 373;BA.debugLine="Select True";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.True,(_val[(int) (2)]).equals(BA.NumberToString(4)),(_val[(int) (2)]).equals(BA.NumberToString(2)),(_val[(int) (2)]).equals(BA.NumberToString(1)))) {
case 0: {
 //BA.debugLineNum = 375;BA.debugLine="tbnSupHigh.Checked = True";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 377;BA.debugLine="tbnSupMed.Checked = True";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 2: {
 //BA.debugLineNum = 379;BA.debugLine="tbnSupLow.Checked = True";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
}
;
 break; }
case 3: {
 //BA.debugLineNum = 382;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 383;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 384;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 385;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 386;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 387;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 388;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 393;BA.debugLine="EditNav";
_editnav();
 //BA.debugLineNum = 395;BA.debugLine="End Sub";
return "";
}
public static String  _editmarketlist() throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub EditMarketList";
 //BA.debugLineNum = 121;BA.debugLine="Q = \"SELECT SM.RecordID, SM.StockTypeDesc, SM.Sto";
mostCurrent._q = "SELECT SM.RecordID, SM.StockTypeDesc, SM.StockLevelRank, SM.ComGrpDesc, SM.CommodDesc FROM StockMarket SM WHERE SM.StationName = ? ORDER BY SM.ComGrpDesc, SM.CommodDesc ASC";
 //BA.debugLineNum = 122;BA.debugLine="StationMarketList = DBUtils.ExecuteMemoryTable(St";
mostCurrent._stationmarketlist = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{mostCurrent._stationname},(int) (0));
 //BA.debugLineNum = 123;BA.debugLine="SMLMax = StationMarketList.Size - 1";
_smlmax = (int) (mostCurrent._stationmarketlist.getSize()-1);
 //BA.debugLineNum = 124;BA.debugLine="If SMLMax <= 0 Then";
if (_smlmax<=0) { 
 //BA.debugLineNum = 125;BA.debugLine="EditVisible(False)";
_editvisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 127;BA.debugLine="EditVisible(True)";
_editvisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 128;BA.debugLine="DisplayEditItem(SMLPosition)";
_displayedititem(_smlposition);
 };
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _editnav() throws Exception{
 //BA.debugLineNum = 430;BA.debugLine="Sub EditNav";
 //BA.debugLineNum = 432;BA.debugLine="If SMLPosition = 0 Then";
if (_smlposition==0) { 
 //BA.debugLineNum = 433;BA.debugLine="btnPrev.Enabled = False";
mostCurrent._btnprev.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 434;BA.debugLine="btnPrevSect.Enabled = False";
mostCurrent._btnprevsect.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 435;BA.debugLine="btnFirst.Enabled = False";
mostCurrent._btnfirst.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 437;BA.debugLine="btnPrev.Enabled = True";
mostCurrent._btnprev.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 438;BA.debugLine="btnPrevSect.Enabled = True";
mostCurrent._btnprevsect.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 439;BA.debugLine="btnFirst.Enabled = True";
mostCurrent._btnfirst.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 441;BA.debugLine="If SMLPosition = SMLMax Then";
if (_smlposition==_smlmax) { 
 //BA.debugLineNum = 442;BA.debugLine="btnNext.Enabled = False";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 443;BA.debugLine="btnNextSect.Enabled = False";
mostCurrent._btnnextsect.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 444;BA.debugLine="btnLast.Enabled = False";
mostCurrent._btnlast.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 446;BA.debugLine="btnNext.Enabled = True";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 447;BA.debugLine="btnNextSect.Enabled = True";
mostCurrent._btnnextsect.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 448;BA.debugLine="btnLast.Enabled = True";
mostCurrent._btnlast.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 451;BA.debugLine="End Sub";
return "";
}
public static String  _editvisible(boolean _status) throws Exception{
 //BA.debugLineNum = 397;BA.debugLine="Sub EditVisible(Status As Boolean)";
 //BA.debugLineNum = 398;BA.debugLine="If Status = True Then";
if (_status==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 399;BA.debugLine="lblCommodGroup.Visible = True";
mostCurrent._lblcommodgroup.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 400;BA.debugLine="lblCommod.Visible = True";
mostCurrent._lblcommod.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 401;BA.debugLine="tbnNA.Visible = True";
mostCurrent._tbnna.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 402;BA.debugLine="tbnDemHigh.Visible = True";
mostCurrent._tbndemhigh.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 403;BA.debugLine="tbnDemMed.Visible = True";
mostCurrent._tbndemmed.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 404;BA.debugLine="tbnDemLow.Visible = True";
mostCurrent._tbndemlow.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 405;BA.debugLine="tbnSupHigh.Visible = True";
mostCurrent._tbnsuphigh.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 406;BA.debugLine="tbnSupMed.Visible = True";
mostCurrent._tbnsupmed.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 407;BA.debugLine="tbnSupLow.Visible = True";
mostCurrent._tbnsuplow.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 408;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 409;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 410;BA.debugLine="pnlDemand.Visible = True";
mostCurrent._pnldemand.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 411;BA.debugLine="pnlSupply.Visible = True";
mostCurrent._pnlsupply.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 413;BA.debugLine="lblCommodGroup.Visible = False";
mostCurrent._lblcommodgroup.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 414;BA.debugLine="lblCommod.Visible = False";
mostCurrent._lblcommod.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 415;BA.debugLine="tbnNA.Visible = False";
mostCurrent._tbnna.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 416;BA.debugLine="tbnDemHigh.Visible = False";
mostCurrent._tbndemhigh.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 417;BA.debugLine="tbnDemMed.Visible = False";
mostCurrent._tbndemmed.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 418;BA.debugLine="tbnDemLow.Visible = False";
mostCurrent._tbndemlow.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 419;BA.debugLine="tbnSupHigh.Visible = False";
mostCurrent._tbnsuphigh.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="tbnSupMed.Visible = False";
mostCurrent._tbnsupmed.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="tbnSupLow.Visible = False";
mostCurrent._tbnsuplow.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 422;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 423;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 424;BA.debugLine="pnlDemand.Visible = False";
mostCurrent._pnldemand.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 425;BA.debugLine="pnlSupply.Visible = False";
mostCurrent._pnlsupply.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 428;BA.debugLine="End Sub";
return "";
}
public static String  _fillmarketlist() throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub FillMarketList";
 //BA.debugLineNum = 114;BA.debugLine="EditMarketList ' Populates the list of Commoditie";
_editmarketlist();
 //BA.debugLineNum = 115;BA.debugLine="Q = \"SELECT SM.CommodDesc AS [Commodity], SM.Stoc";
mostCurrent._q = "SELECT SM.CommodDesc AS [Commodity], SM.StockTypeDesc AS [Demand/Supply], SL.StockLevelDesc AS [Stock Level] FROM StockMarket SM LEFT JOIN StockLevels SL ON SM.StockLevelRank = SL.StockLevelRank WHERE SM.StationName = ? AND SM.ComGrpDesc = ? AND SM.StockTypeDesc != 'Not Tradeable' ORDER BY SM.ComGrpDesc, SM.CommodDesc ASC";
 //BA.debugLineNum = 116;BA.debugLine="wbvStockList.LoadHtml(DBUtils.ExecuteHtml(Starter";
mostCurrent._wbvstocklist.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{mostCurrent._stationname,mostCurrent._lblcommodgroup.getText()},(int) (0),anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnFirst As Button";
mostCurrent._btnfirst = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnPrevSect As Button";
mostCurrent._btnprevsect = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnNextSect As Button";
mostCurrent._btnnextsect = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnLast As Button";
mostCurrent._btnlast = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblCommod As Label";
mostCurrent._lblcommod = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblCommodGroup As Label";
mostCurrent._lblcommodgroup = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblComGrp As Label";
mostCurrent._lblcomgrp = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblDemand As Label";
mostCurrent._lbldemand = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblSupply As Label";
mostCurrent._lblsupply = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private spnStation As Spinner";
mostCurrent._spnstation = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private tbnNA As ToggleButton";
mostCurrent._tbnna = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private tbnDemHigh As ToggleButton";
mostCurrent._tbndemhigh = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private tbnDemMed As ToggleButton";
mostCurrent._tbndemmed = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private tbnDemLow As ToggleButton";
mostCurrent._tbndemlow = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private tbnSupHigh As ToggleButton";
mostCurrent._tbnsuphigh = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private tbnSupMed As ToggleButton";
mostCurrent._tbnsupmed = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private tbnSupLow As ToggleButton";
mostCurrent._tbnsuplow = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private wbvStockList As WebView";
mostCurrent._wbvstocklist = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private acetSystemName As AutoCompleteEditText";
mostCurrent._acetsystemname = new anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim SystemName As String";
mostCurrent._systemname = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim StationName As String";
mostCurrent._stationname = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim SMLMax As Int";
_smlmax = 0;
 //BA.debugLineNum = 47;BA.debugLine="Dim SMLPosition As Int";
_smlposition = 0;
 //BA.debugLineNum = 48;BA.debugLine="Dim SMItem As Int";
_smitem = 0;
 //BA.debugLineNum = 50;BA.debugLine="Dim LoadedType As String";
mostCurrent._loadedtype = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim LoadedLvl As Int";
_loadedlvl = 0;
 //BA.debugLineNum = 52;BA.debugLine="Dim ItemChanged As Boolean";
_itemchanged = false;
 //BA.debugLineNum = 54;BA.debugLine="Dim SystemLocation As List";
mostCurrent._systemlocation = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 55;BA.debugLine="Dim StationMarketList As List";
mostCurrent._stationmarketlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 57;BA.debugLine="Dim pnlDemand As Panel";
mostCurrent._pnldemand = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim pnlSupply As Panel";
mostCurrent._pnlsupply = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim Q As String";
mostCurrent._q = "";
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _initsystemlocation() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
 //BA.debugLineNum = 93;BA.debugLine="Sub InitSystemLocation";
 //BA.debugLineNum = 94;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 95;BA.debugLine="Dim CursSystems As Cursor";
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 97;BA.debugLine="SystemLocation.Initialize";
mostCurrent._systemlocation.Initialize();
 //BA.debugLineNum = 99;BA.debugLine="Q = \"SELECT SystemName FROM Systems WHERE LYfromC";
mostCurrent._q = "SELECT SystemName FROM Systems WHERE LYfromCurrent < "+BA.NumberToString(mostCurrent._starter._maxlydist)+" ORDER BY SystemName ASC";
 //BA.debugLineNum = 100;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery(Q)";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 101;BA.debugLine="If CursSystems.RowCount > 0 Then";
if (_curssystems.getRowCount()>0) { 
 //BA.debugLineNum = 102;BA.debugLine="SystemLocation.Add(\"Not Set\")";
mostCurrent._systemlocation.Add((Object)("Not Set"));
 //BA.debugLineNum = 103;BA.debugLine="For i = 0 To CursSystems.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (_curssystems.getRowCount()-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 104;BA.debugLine="CursSystems.Position = i";
_curssystems.setPosition(_i);
 //BA.debugLineNum = 105;BA.debugLine="SystemLocation.Add(CursSystems.GetString(\"Syste";
mostCurrent._systemlocation.Add((Object)(_curssystems.GetString("SystemName")));
 }
};
 };
 //BA.debugLineNum = 108;BA.debugLine="SystemLocation.Set(0,\"\")";
mostCurrent._systemlocation.Set((int) (0),(Object)(""));
 //BA.debugLineNum = 109;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _loadsystemlocation() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub LoadSystemLocation";
 //BA.debugLineNum = 88;BA.debugLine="acetSystemName.Text = Starter.CurrLocation";
mostCurrent._acetsystemname.setText((Object)(mostCurrent._starter._currlocation));
 //BA.debugLineNum = 89;BA.debugLine="acetSystemName_EnterPressed";
_acetsystemname_enterpressed();
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _saveitem() throws Exception{
String _stocktypevalue = "";
int _stocklevelvalue = 0;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 453;BA.debugLine="Sub SaveItem";
 //BA.debugLineNum = 454;BA.debugLine="Dim StockTypeValue As String";
_stocktypevalue = "";
 //BA.debugLineNum = 455;BA.debugLine="Dim StockLevelValue As Int";
_stocklevelvalue = 0;
 //BA.debugLineNum = 456;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 458;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 459;BA.debugLine="whereclause.Put(\"RecordID\", SMItem)";
_whereclause.Put((Object)("RecordID"),(Object)(_smitem));
 //BA.debugLineNum = 461;BA.debugLine="Select True";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnna.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbndemhigh.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbndemmed.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbndemlow.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnsuphigh.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnsupmed.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnsuplow.getChecked()==anywheresoftware.b4a.keywords.Common.True)) {
case 0: {
 //BA.debugLineNum = 463;BA.debugLine="StockTypeValue = \"Not Tradeable\"";
_stocktypevalue = "Not Tradeable";
 //BA.debugLineNum = 464;BA.debugLine="StockLevelValue = 0";
_stocklevelvalue = (int) (0);
 break; }
case 1: {
 //BA.debugLineNum = 466;BA.debugLine="StockTypeValue = \"Demand\"";
_stocktypevalue = "Demand";
 //BA.debugLineNum = 467;BA.debugLine="StockLevelValue = 4";
_stocklevelvalue = (int) (4);
 break; }
case 2: {
 //BA.debugLineNum = 469;BA.debugLine="StockTypeValue = \"Demand\"";
_stocktypevalue = "Demand";
 //BA.debugLineNum = 470;BA.debugLine="StockLevelValue = 2";
_stocklevelvalue = (int) (2);
 break; }
case 3: {
 //BA.debugLineNum = 472;BA.debugLine="StockTypeValue = \"Demand\"";
_stocktypevalue = "Demand";
 //BA.debugLineNum = 473;BA.debugLine="StockLevelValue = 1";
_stocklevelvalue = (int) (1);
 break; }
case 4: {
 //BA.debugLineNum = 475;BA.debugLine="StockTypeValue = \"Supply\"";
_stocktypevalue = "Supply";
 //BA.debugLineNum = 476;BA.debugLine="StockLevelValue = 4";
_stocklevelvalue = (int) (4);
 break; }
case 5: {
 //BA.debugLineNum = 478;BA.debugLine="StockTypeValue = \"Supply\"";
_stocktypevalue = "Supply";
 //BA.debugLineNum = 479;BA.debugLine="StockLevelValue = 2";
_stocklevelvalue = (int) (2);
 break; }
case 6: {
 //BA.debugLineNum = 481;BA.debugLine="StockTypeValue = \"Supply\"";
_stocktypevalue = "Supply";
 //BA.debugLineNum = 482;BA.debugLine="StockLevelValue = 1";
_stocklevelvalue = (int) (1);
 break; }
}
;
 //BA.debugLineNum = 485;BA.debugLine="If StockTypeValue <> LoadedType Then";
if ((_stocktypevalue).equals(mostCurrent._loadedtype) == false) { 
 //BA.debugLineNum = 486;BA.debugLine="ItemChanged = True";
_itemchanged = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 488;BA.debugLine="If StockLevelValue <> LoadedLvl Then";
if (_stocklevelvalue!=_loadedlvl) { 
 //BA.debugLineNum = 489;BA.debugLine="ItemChanged = True";
_itemchanged = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 492;BA.debugLine="If ItemChanged = True Then";
if (_itemchanged==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 493;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"StockMark";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockMarket","StockTypeDesc",(Object)(_stocktypevalue),_whereclause);
 //BA.debugLineNum = 494;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"StockMark";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockMarket","StockLevelRank",(Object)(_stocklevelvalue),_whereclause);
 //BA.debugLineNum = 495;BA.debugLine="ItemChanged = False";
_itemchanged = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 496;BA.debugLine="btnNext_Click";
_btnnext_click();
 };
 //BA.debugLineNum = 499;BA.debugLine="End Sub";
return "";
}
public static String  _settogglebuttoncolour() throws Exception{
boolean _buttonselected = false;
 //BA.debugLineNum = 305;BA.debugLine="Sub SetToggleButtonColour";
 //BA.debugLineNum = 306;BA.debugLine="SaveItem";
_saveitem();
 //BA.debugLineNum = 308;BA.debugLine="Dim ButtonSelected As Boolean";
_buttonselected = false;
 //BA.debugLineNum = 310;BA.debugLine="ButtonSelected = False";
_buttonselected = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 312;BA.debugLine="Select True";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnna.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbndemhigh.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbndemmed.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbndemlow.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnsuphigh.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnsupmed.getChecked()==anywheresoftware.b4a.keywords.Common.True,mostCurrent._tbnsuplow.getChecked()==anywheresoftware.b4a.keywords.Common.True)) {
case 0: {
 //BA.debugLineNum = 314;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
case 1: {
 //BA.debugLineNum = 316;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
case 2: {
 //BA.debugLineNum = 318;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
case 3: {
 //BA.debugLineNum = 320;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
case 4: {
 //BA.debugLineNum = 322;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
case 5: {
 //BA.debugLineNum = 324;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
case 6: {
 //BA.debugLineNum = 326;BA.debugLine="ButtonSelected = True";
_buttonselected = anywheresoftware.b4a.keywords.Common.True;
 break; }
}
;
 //BA.debugLineNum = 329;BA.debugLine="If ButtonSelected = True Then";
if (_buttonselected==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 330;BA.debugLine="tbnNA.Background = Functions.ToggleButGood";
mostCurrent._tbnna.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 331;BA.debugLine="tbnDemHigh.Background = Functions.ToggleButGood";
mostCurrent._tbndemhigh.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 332;BA.debugLine="tbnDemMed.Background = Functions.ToggleButGood";
mostCurrent._tbndemmed.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 333;BA.debugLine="tbnDemLow.Background = Functions.ToggleButGood";
mostCurrent._tbndemlow.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 334;BA.debugLine="tbnSupHigh.Background = Functions.ToggleButGood";
mostCurrent._tbnsuphigh.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 335;BA.debugLine="tbnSupMed.Background = Functions.ToggleButGood";
mostCurrent._tbnsupmed.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 336;BA.debugLine="tbnSupLow.Background = Functions.ToggleButGood";
mostCurrent._tbnsuplow.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebutgood(mostCurrent.activityBA).getObject()));
 }else {
 //BA.debugLineNum = 338;BA.debugLine="tbnNA.Background = Functions.ToggleBut";
mostCurrent._tbnna.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 339;BA.debugLine="tbnDemHigh.Background = Functions.ToggleBut";
mostCurrent._tbndemhigh.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 340;BA.debugLine="tbnDemMed.Background = Functions.ToggleBut";
mostCurrent._tbndemmed.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 341;BA.debugLine="tbnDemLow.Background = Functions.ToggleBut";
mostCurrent._tbndemlow.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 342;BA.debugLine="tbnSupHigh.Background = Functions.ToggleBut";
mostCurrent._tbnsuphigh.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 343;BA.debugLine="tbnSupMed.Background = Functions.ToggleBut";
mostCurrent._tbnsupmed.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 344;BA.debugLine="tbnSupLow.Background = Functions.ToggleBut";
mostCurrent._tbnsuplow.setBackground((android.graphics.drawable.Drawable)(mostCurrent._functions._togglebut(mostCurrent.activityBA).getObject()));
 };
 //BA.debugLineNum = 346;BA.debugLine="End Sub";
return "";
}
public static String  _spnstation_itemclick(int _position,Object _value) throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstations = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstock = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curscommod = null;
anywheresoftware.b4a.objects.collections.Map _record = null;
int _econnum = 0;
String _r = "";
int _totalcount = 0;
 //BA.debugLineNum = 501;BA.debugLine="Sub spnStation_ItemClick (Position As Int, Value A";
 //BA.debugLineNum = 502;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 503;BA.debugLine="Dim CursStations As Cursor";
_cursstations = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 504;BA.debugLine="Dim CursStock As Cursor";
_cursstock = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 505;BA.debugLine="Dim CursCommod As Cursor";
_curscommod = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 506;BA.debugLine="Dim record As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 507;BA.debugLine="Dim econnum As Int";
_econnum = 0;
 //BA.debugLineNum = 509;BA.debugLine="Q = \"SELECT * FROM Stations WHERE StationName = ?";
mostCurrent._q = "SELECT * FROM Stations WHERE StationName = ?";
 //BA.debugLineNum = 510;BA.debugLine="CursStations = Starter.SQLExec.ExecQuery2(Q, Arra";
_cursstations.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{BA.ObjectToString(_value)})));
 //BA.debugLineNum = 511;BA.debugLine="If CursStations.RowCount > 0 Then";
if (_cursstations.getRowCount()>0) { 
 //BA.debugLineNum = 512;BA.debugLine="CursStations.Position = 0 'Sets the row pointer";
_cursstations.setPosition((int) (0));
 //BA.debugLineNum = 513;BA.debugLine="StationName = CursStations.GetString(\"StationNam";
mostCurrent._stationname = _cursstations.GetString("StationName");
 //BA.debugLineNum = 514;BA.debugLine="SystemName = CursStations.GetString(\"SystemName\"";
mostCurrent._systemname = _cursstations.GetString("SystemName");
 //BA.debugLineNum = 515;BA.debugLine="econnum = CursStations.GetInt(\"EconomyNum\")";
_econnum = _cursstations.GetInt("EconomyNum");
 };
 //BA.debugLineNum = 517;BA.debugLine="CursStations.Close";
_cursstations.Close();
 //BA.debugLineNum = 518;BA.debugLine="Q = \"SELECT * FROM StockMarket WHERE StationName";
mostCurrent._q = "SELECT * FROM StockMarket WHERE StationName = ?";
 //BA.debugLineNum = 519;BA.debugLine="CursStock = Starter.SQLExec.ExecQuery2(Q, Array A";
_cursstock.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._stationname})));
 //BA.debugLineNum = 520;BA.debugLine="If econnum <> 0 Then";
if (_econnum!=0) { 
 //BA.debugLineNum = 521;BA.debugLine="If CursStock.RowCount = 0 Then";
if (_cursstock.getRowCount()==0) { 
 //BA.debugLineNum = 522;BA.debugLine="Q = \"SELECT C.CommodDesc, C.ComGrpDesc FROM Com";
mostCurrent._q = "SELECT C.CommodDesc, C.ComGrpDesc FROM Commodities C ORDER BY C.ComGrpDesc, C.CommodDesc ASC";
 //BA.debugLineNum = 523;BA.debugLine="CursCommod = Starter.SQLExec.ExecQuery(Q)";
_curscommod.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 524;BA.debugLine="If CursCommod.RowCount > 0 Then";
if (_curscommod.getRowCount()>0) { 
 //BA.debugLineNum = 525;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 526;BA.debugLine="record.Put(\"RecordID\", Null)";
_record.Put((Object)("RecordID"),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 527;BA.debugLine="record.Put(\"SystemName\", SystemName)";
_record.Put((Object)("SystemName"),(Object)(mostCurrent._systemname));
 //BA.debugLineNum = 528;BA.debugLine="record.Put(\"StationName\", StationName)";
_record.Put((Object)("StationName"),(Object)(mostCurrent._stationname));
 //BA.debugLineNum = 529;BA.debugLine="For i = 0 To CursCommod.RowCount - 1";
{
final int step27 = 1;
final int limit27 = (int) (_curscommod.getRowCount()-1);
for (_i = (int) (0) ; (step27 > 0 && _i <= limit27) || (step27 < 0 && _i >= limit27); _i = ((int)(0 + _i + step27)) ) {
 //BA.debugLineNum = 530;BA.debugLine="CursCommod.Position = i";
_curscommod.setPosition(_i);
 //BA.debugLineNum = 531;BA.debugLine="record.Put(\"ComGrpDesc\", CursCommod.GetString";
_record.Put((Object)("ComGrpDesc"),(Object)(_curscommod.GetString("ComGrpDesc")));
 //BA.debugLineNum = 532;BA.debugLine="record.Put(\"CommodDesc\", CursCommod.GetString";
_record.Put((Object)("CommodDesc"),(Object)(_curscommod.GetString("CommodDesc")));
 //BA.debugLineNum = 533;BA.debugLine="record.Put(\"StockTypeDesc\", \"\")";
_record.Put((Object)("StockTypeDesc"),(Object)(""));
 //BA.debugLineNum = 534;BA.debugLine="record.Put(\"StockLevelRank\", 0)";
_record.Put((Object)("StockLevelRank"),(Object)(0));
 //BA.debugLineNum = 535;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"Sto";
mostCurrent._sqlutils._table_insertmap(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockMarket",_record);
 }
};
 };
 //BA.debugLineNum = 539;BA.debugLine="CursCommod.Close";
_curscommod.Close();
 }else {
 //BA.debugLineNum = 541;BA.debugLine="Dim R As String";
_r = "";
 //BA.debugLineNum = 542;BA.debugLine="Dim totalcount As Int";
_totalcount = 0;
 //BA.debugLineNum = 543;BA.debugLine="Q = \"SELECT C.CommodDesc, C.ComGrpDesc FROM Com";
mostCurrent._q = "SELECT C.CommodDesc, C.ComGrpDesc FROM Commodities C ORDER BY C.ComGrpDesc, C.CommodDesc ASC";
 //BA.debugLineNum = 544;BA.debugLine="CursCommod = Starter.SQLExec.ExecQuery(Q)";
_curscommod.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 545;BA.debugLine="If CursCommod.RowCount > 0 Then";
if (_curscommod.getRowCount()>0) { 
 //BA.debugLineNum = 546;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 547;BA.debugLine="For i = 0 To CursCommod.RowCount - 1";
{
final int step44 = 1;
final int limit44 = (int) (_curscommod.getRowCount()-1);
for (_i = (int) (0) ; (step44 > 0 && _i <= limit44) || (step44 < 0 && _i >= limit44); _i = ((int)(0 + _i + step44)) ) {
 //BA.debugLineNum = 548;BA.debugLine="CursCommod.Position = i";
_curscommod.setPosition(_i);
 //BA.debugLineNum = 549;BA.debugLine="R = \"SELECT COUNT(*) FROM StockMarket WHERE S";
_r = "SELECT COUNT(*) FROM StockMarket WHERE StationName = ? AND CommodDesc = ?";
 //BA.debugLineNum = 550;BA.debugLine="totalcount = Starter.SQLExec.ExecQuerySingleR";
_totalcount = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(_r,new String[]{mostCurrent._stationname,_curscommod.GetString("CommodDesc")})));
 //BA.debugLineNum = 551;BA.debugLine="If totalcount = 0 Then";
if (_totalcount==0) { 
 //BA.debugLineNum = 552;BA.debugLine="record.Put(\"RecordID\", Null)";
_record.Put((Object)("RecordID"),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 553;BA.debugLine="record.Put(\"SystemName\", SystemName)";
_record.Put((Object)("SystemName"),(Object)(mostCurrent._systemname));
 //BA.debugLineNum = 554;BA.debugLine="record.Put(\"StationName\", StationName)";
_record.Put((Object)("StationName"),(Object)(mostCurrent._stationname));
 //BA.debugLineNum = 555;BA.debugLine="record.Put(\"ComGrpDesc\", CursCommod.GetStrin";
_record.Put((Object)("ComGrpDesc"),(Object)(_curscommod.GetString("ComGrpDesc")));
 //BA.debugLineNum = 556;BA.debugLine="record.Put(\"CommodDesc\", CursCommod.GetStrin";
_record.Put((Object)("CommodDesc"),(Object)(_curscommod.GetString("CommodDesc")));
 //BA.debugLineNum = 557;BA.debugLine="record.Put(\"StockTypeDesc\", \"\")";
_record.Put((Object)("StockTypeDesc"),(Object)(""));
 //BA.debugLineNum = 558;BA.debugLine="record.Put(\"StockLevelRank\", 0)";
_record.Put((Object)("StockLevelRank"),(Object)(0));
 //BA.debugLineNum = 559;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"St";
mostCurrent._sqlutils._table_insertmap(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockMarket",_record);
 };
 }
};
 };
 //BA.debugLineNum = 563;BA.debugLine="CursCommod.Close";
_curscommod.Close();
 };
 //BA.debugLineNum = 565;BA.debugLine="CursStock.Close";
_cursstock.Close();
 };
 //BA.debugLineNum = 568;BA.debugLine="SMLPosition = 0";
_smlposition = (int) (0);
 //BA.debugLineNum = 570;BA.debugLine="FillMarketList";
_fillmarketlist();
 //BA.debugLineNum = 572;BA.debugLine="End Sub";
return "";
}
public static String  _tbndemhigh_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Sub tbnDemHigh_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 232;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 233;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 234;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 235;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 236;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 237;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 239;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _tbndemlow_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub tbnDemLow_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 256;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 257;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 258;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 260;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 261;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 262;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 263;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _tbndemmed_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Sub tbnDemMed_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 244;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 245;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 246;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 247;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 248;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 249;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 250;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _tbnna_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Sub tbnNA_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 217;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 218;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 219;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 220;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 221;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 222;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 224;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 226;BA.debugLine="If Checked = False Then";
if (_checked==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 227;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _tbnsuphigh_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 267;BA.debugLine="Sub tbnSupHigh_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 268;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 269;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 270;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 272;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 273;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 274;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 277;BA.debugLine="End Sub";
return "";
}
public static String  _tbnsuplow_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 291;BA.debugLine="Sub tbnSupLow_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 292;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 293;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 294;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 296;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 297;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="tbnSupMed.Checked = False";
mostCurrent._tbnsupmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _tbnsupmed_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 279;BA.debugLine="Sub tbnSupMed_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 280;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 281;BA.debugLine="tbnNA.Checked = False";
mostCurrent._tbnna.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="tbnDemHigh.Checked = False";
mostCurrent._tbndemhigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="tbnDemMed.Checked = False";
mostCurrent._tbndemmed.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 284;BA.debugLine="tbnDemLow.Checked = False";
mostCurrent._tbndemlow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 285;BA.debugLine="tbnSupHigh.Checked = False";
mostCurrent._tbnsuphigh.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 286;BA.debugLine="tbnSupLow.Checked = False";
mostCurrent._tbnsuplow.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 287;BA.debugLine="SetToggleButtonColour";
_settogglebuttoncolour();
 };
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
}
