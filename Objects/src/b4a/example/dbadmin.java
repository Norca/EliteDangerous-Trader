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

public class dbadmin extends Activity implements B4AActivity{
	public static dbadmin mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.dbadmin");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (dbadmin).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.dbadmin");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.dbadmin", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (dbadmin) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (dbadmin) Resume **");
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
		return dbadmin.class;
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
        BA.LogInfo("** Activity (dbadmin) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (dbadmin) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _btncommod = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnalleg = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnpopu = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbncomgrp = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbncommod = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnrares = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbncurrloc = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbngov = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnmaxly = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnstations = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnstattyp = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnstklvl = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnstktyp = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnstock = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnsystems = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbntempstat = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbngalaver = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnfactions = null;
public static String _q = "";
public anywheresoftware.b4a.sql.SQL.CursorWrapper _curs = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"DBAdmin\")";
mostCurrent._activity.LoadLayout("DBAdmin",mostCurrent.activityBA);
 //BA.debugLineNum = 46;BA.debugLine="Functions.SetColours(Activity)";
mostCurrent._functions._setcolours(mostCurrent.activityBA,mostCurrent._activity);
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 51;BA.debugLine="RefreshButtons";
_refreshbuttons();
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _btncommod_click() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub btnCommod_Click";
 //BA.debugLineNum = 60;BA.debugLine="If tbnCommod.Checked = False Then";
if (mostCurrent._tbncommod.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 61;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 63;BA.debugLine="StartActivity(\"CommodUpdate\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("CommodUpdate"));
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _btnexit_click() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub btnExit_Click";
 //BA.debugLineNum = 68;BA.debugLine="EDTables.DatabaseSetup";
mostCurrent._edtables._databasesetup(mostCurrent.activityBA);
 //BA.debugLineNum = 69;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private btnCommod As Button";
mostCurrent._btncommod = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private tbnAlleg As ToggleButton";
mostCurrent._tbnalleg = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private tbnPopu As ToggleButton";
mostCurrent._tbnpopu = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private tbnComGrp As ToggleButton";
mostCurrent._tbncomgrp = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private tbnCommod As ToggleButton";
mostCurrent._tbncommod = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private tbnRares As ToggleButton";
mostCurrent._tbnrares = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private tbnCurrLoc As ToggleButton";
mostCurrent._tbncurrloc = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private tbnEcon As ToggleButton";
mostCurrent._tbnecon = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private tbnGov As ToggleButton";
mostCurrent._tbngov = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private tbnMaxLY As ToggleButton";
mostCurrent._tbnmaxly = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private tbnStations As ToggleButton";
mostCurrent._tbnstations = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private tbnStatTyp As ToggleButton";
mostCurrent._tbnstattyp = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private tbnStkLvl As ToggleButton";
mostCurrent._tbnstklvl = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private tbnStkTyp As ToggleButton";
mostCurrent._tbnstktyp = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private tbnStock As ToggleButton";
mostCurrent._tbnstock = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private tbnSystems As ToggleButton";
mostCurrent._tbnsystems = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private tbnTempStat As ToggleButton";
mostCurrent._tbntempstat = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private tbnGalAver As ToggleButton";
mostCurrent._tbngalaver = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private tbnFactions As ToggleButton";
mostCurrent._tbnfactions = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Q As String";
mostCurrent._q = "";
 //BA.debugLineNum = 39;BA.debugLine="Private Curs As Cursor";
mostCurrent._curs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _refreshbuttons() throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub RefreshButtons";
 //BA.debugLineNum = 76;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Systems\"";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Systems")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 77;BA.debugLine="tbnSystems.Checked = True";
mostCurrent._tbnsystems.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="Q = \"SELECT * FROM Systems\"";
mostCurrent._q = "SELECT * FROM Systems";
 //BA.debugLineNum = 79;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 80;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 81;BA.debugLine="tbnSystems.TextOn = \"SYSTEMS (\" & Curs.RowCount";
mostCurrent._tbnsystems.setTextOn("SYSTEMS ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 83;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 85;BA.debugLine="tbnSystems.Checked = False";
mostCurrent._tbnsystems.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 90;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Stations";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Stations")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 91;BA.debugLine="tbnStations.Checked = True";
mostCurrent._tbnstations.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 92;BA.debugLine="Q = \"SELECT * FROM Stations\"";
mostCurrent._q = "SELECT * FROM Stations";
 //BA.debugLineNum = 93;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 94;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 95;BA.debugLine="tbnStations.TextOn = \"STATIONS (\" & Curs.RowCou";
mostCurrent._tbnstations.setTextOn("STATIONS ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 97;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 99;BA.debugLine="tbnStations.Checked = False";
mostCurrent._tbnstations.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 104;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StockMar";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockMarket")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 105;BA.debugLine="tbnStock.Checked = True";
mostCurrent._tbnstock.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 106;BA.debugLine="Q = \"SELECT * FROM StockMarket\"";
mostCurrent._q = "SELECT * FROM StockMarket";
 //BA.debugLineNum = 107;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 108;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 109;BA.debugLine="tbnStock.TextOn = \"STOCK MARKET (\" & Curs.RowCo";
mostCurrent._tbnstock.setTextOn("STOCK MARKET ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 111;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 113;BA.debugLine="tbnStock.Checked = False";
mostCurrent._tbnstock.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 118;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Factions";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Factions")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 119;BA.debugLine="tbnFactions.Checked = True";
mostCurrent._tbnfactions.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="Q = \"SELECT * FROM Factions\"";
mostCurrent._q = "SELECT * FROM Factions";
 //BA.debugLineNum = 121;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 122;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 123;BA.debugLine="tbnFactions.TextOn = \"FACTIONS (\" & Curs.RowCou";
mostCurrent._tbnfactions.setTextOn("FACTIONS ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 125;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 127;BA.debugLine="tbnFactions.Checked = False";
mostCurrent._tbnfactions.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 132;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Commodit";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"CommodityGroups")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 133;BA.debugLine="tbnComGrp.Checked = True";
mostCurrent._tbncomgrp.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 134;BA.debugLine="Q = \"SELECT * FROM CommodityGroups\"";
mostCurrent._q = "SELECT * FROM CommodityGroups";
 //BA.debugLineNum = 135;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 136;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 137;BA.debugLine="tbnComGrp.TextOn = \"COMMODITY GROUPS (\" & Curs.";
mostCurrent._tbncomgrp.setTextOn("COMMODITY GROUPS ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 139;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 141;BA.debugLine="tbnComGrp.Checked = False";
mostCurrent._tbncomgrp.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 146;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Commodit";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Commodities")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 147;BA.debugLine="tbnCommod.Checked = True";
mostCurrent._tbncommod.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 148;BA.debugLine="Q = \"SELECT * FROM Commodities\"";
mostCurrent._q = "SELECT * FROM Commodities";
 //BA.debugLineNum = 149;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 150;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 151;BA.debugLine="tbnCommod.TextOn = \"COMMODITIES (\" & Curs.RowCo";
mostCurrent._tbncommod.setTextOn("COMMODITIES ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 153;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 //BA.debugLineNum = 154;BA.debugLine="btnCommod.Enabled = True";
mostCurrent._btncommod.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 156;BA.debugLine="tbnCommod.Checked = False";
mostCurrent._tbncommod.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="btnCommod.Enabled = False";
mostCurrent._btncommod.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 162;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"RareComm";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"RareCommodities")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 163;BA.debugLine="tbnRares.Checked = True";
mostCurrent._tbnrares.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 164;BA.debugLine="Q = \"SELECT * FROM RareCommodities\"";
mostCurrent._q = "SELECT * FROM RareCommodities";
 //BA.debugLineNum = 165;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 166;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 167;BA.debugLine="tbnRares.TextOn = \"RARE COMMODITIES (\" & Curs.R";
mostCurrent._tbnrares.setTextOn("RARE COMMODITIES ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 169;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 171;BA.debugLine="tbnRares.Checked = False";
mostCurrent._tbnrares.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 176;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StockTyp";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockTypes")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 177;BA.debugLine="tbnStkTyp.Checked = True";
mostCurrent._tbnstktyp.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 178;BA.debugLine="Q = \"SELECT * FROM StockTypes\"";
mostCurrent._q = "SELECT * FROM StockTypes";
 //BA.debugLineNum = 179;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 180;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 181;BA.debugLine="tbnStkTyp.TextOn = \"STOCK TYPES (\" & Curs.RowCo";
mostCurrent._tbnstktyp.setTextOn("STOCK TYPES ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 183;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 185;BA.debugLine="tbnStkTyp.Checked = False";
mostCurrent._tbnstktyp.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 190;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StockLev";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockLevels")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 191;BA.debugLine="tbnStkLvl.Checked = True";
mostCurrent._tbnstklvl.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 192;BA.debugLine="Q = \"SELECT * FROM StockLevels\"";
mostCurrent._q = "SELECT * FROM StockLevels";
 //BA.debugLineNum = 193;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 194;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 195;BA.debugLine="tbnStkLvl.TextOn = \"STOCK LEVELS (\" & Curs.RowC";
mostCurrent._tbnstklvl.setTextOn("STOCK LEVELS ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 197;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 199;BA.debugLine="tbnStkLvl.Checked = False";
mostCurrent._tbnstklvl.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 204;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Governme";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Governments")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 205;BA.debugLine="tbnGov.Checked = True";
mostCurrent._tbngov.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 206;BA.debugLine="Q = \"SELECT * FROM Governments\"";
mostCurrent._q = "SELECT * FROM Governments";
 //BA.debugLineNum = 207;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 208;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 209;BA.debugLine="tbnGov.TextOn = \"GOVERNMENTS (\" & Curs.RowCount";
mostCurrent._tbngov.setTextOn("GOVERNMENTS ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 211;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 213;BA.debugLine="tbnGov.Checked = False";
mostCurrent._tbngov.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 218;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Economie";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Economies")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 219;BA.debugLine="tbnEcon.Checked = True";
mostCurrent._tbnecon.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 220;BA.debugLine="Q = \"SELECT * FROM Economies\"";
mostCurrent._q = "SELECT * FROM Economies";
 //BA.debugLineNum = 221;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 222;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 223;BA.debugLine="tbnEcon.TextOn = \"ECONOMIES (\" & Curs.RowCount";
mostCurrent._tbnecon.setTextOn("ECONOMIES ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 225;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 227;BA.debugLine="tbnEcon.Checked = False";
mostCurrent._tbnecon.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 232;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Allegian";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Allegiance")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 233;BA.debugLine="tbnAlleg.Checked = True";
mostCurrent._tbnalleg.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 234;BA.debugLine="Q = \"SELECT * FROM Allegiance\"";
mostCurrent._q = "SELECT * FROM Allegiance";
 //BA.debugLineNum = 235;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 236;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 237;BA.debugLine="tbnAlleg.TextOn = \"ALLEGIANCE (\" & Curs.RowCoun";
mostCurrent._tbnalleg.setTextOn("ALLEGIANCE ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 239;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 241;BA.debugLine="tbnAlleg.Checked = False";
mostCurrent._tbnalleg.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 246;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StationT";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StationTypes")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 247;BA.debugLine="tbnStatTyp.Checked = True";
mostCurrent._tbnstattyp.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 248;BA.debugLine="Q = \"SELECT * FROM StationTypes\"";
mostCurrent._q = "SELECT * FROM StationTypes";
 //BA.debugLineNum = 249;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 250;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 251;BA.debugLine="tbnStatTyp.TextOn = \"STATION TYPES (\" & Curs.Ro";
mostCurrent._tbnstattyp.setTextOn("STATION TYPES ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 253;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 255;BA.debugLine="tbnStatTyp.Checked = False";
mostCurrent._tbnstattyp.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 260;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Populati";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Population")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 261;BA.debugLine="tbnPopu.Checked = True";
mostCurrent._tbnpopu.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 262;BA.debugLine="Q = \"SELECT * FROM Population\"";
mostCurrent._q = "SELECT * FROM Population";
 //BA.debugLineNum = 263;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 264;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 265;BA.debugLine="tbnPopu.TextOn = \"POPULATION (\" & Curs.RowCount";
mostCurrent._tbnpopu.setTextOn("POPULATION ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 267;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 269;BA.debugLine="tbnPopu.Checked = False";
mostCurrent._tbnpopu.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 274;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Location";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Location")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 275;BA.debugLine="tbnCurrLoc.Checked = True";
mostCurrent._tbncurrloc.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 276;BA.debugLine="Q = \"SELECT * FROM Location\"";
mostCurrent._q = "SELECT * FROM Location";
 //BA.debugLineNum = 277;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 278;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 279;BA.debugLine="tbnCurrLoc.TextOn = \"LOCATION (\" & Curs.RowCoun";
mostCurrent._tbncurrloc.setTextOn("LOCATION ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 281;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 283;BA.debugLine="tbnCurrLoc.Checked = False";
mostCurrent._tbncurrloc.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 288;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"MaxLYTra";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"MaxLYTradeDistance")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 289;BA.debugLine="tbnMaxLY.Checked = True";
mostCurrent._tbnmaxly.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 290;BA.debugLine="Q = \"SELECT * FROM MaxLYTradeDistance\"";
mostCurrent._q = "SELECT * FROM MaxLYTradeDistance";
 //BA.debugLineNum = 291;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 292;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 293;BA.debugLine="tbnMaxLY.TextOn = \"MAX TRADE DISTANCE (\" & Curs";
mostCurrent._tbnmaxly.setTextOn("MAX TRADE DISTANCE ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 295;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 297;BA.debugLine="tbnMaxLY.Checked = False";
mostCurrent._tbnmaxly.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 302;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"TempStat";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"TempStations")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 303;BA.debugLine="tbnTempStat.Checked = True";
mostCurrent._tbntempstat.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 304;BA.debugLine="Q = \"SELECT * FROM TempStations\"";
mostCurrent._q = "SELECT * FROM TempStations";
 //BA.debugLineNum = 305;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 306;BA.debugLine="If Curs.RowCount = 0 Then";
if (mostCurrent._curs.getRowCount()==0) { 
 //BA.debugLineNum = 307;BA.debugLine="tbnTempStat.Checked = False";
mostCurrent._tbntempstat.setChecked(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 309;BA.debugLine="tbnTempStat.Checked = True";
mostCurrent._tbntempstat.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 311;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 313;BA.debugLine="tbnTempStat.Checked =False";
mostCurrent._tbntempstat.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 318;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Galactic";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"GalacticPrices")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 319;BA.debugLine="tbnGalAver.Checked = True";
mostCurrent._tbngalaver.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 320;BA.debugLine="Q = \"SELECT * FROM GalacticPrices\"";
mostCurrent._q = "SELECT * FROM GalacticPrices";
 //BA.debugLineNum = 321;BA.debugLine="Curs = Starter.SQLExec.ExecQuery(Q)";
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 322;BA.debugLine="If Curs.RowCount > 0 Then";
if (mostCurrent._curs.getRowCount()>0) { 
 //BA.debugLineNum = 323;BA.debugLine="tbnGalAver.TextOn = \"GALACTIC PRICES (\" & Curs.";
mostCurrent._tbngalaver.setTextOn("GALACTIC PRICES ("+BA.NumberToString(mostCurrent._curs.getRowCount())+")");
 };
 //BA.debugLineNum = 325;BA.debugLine="Curs.Close";
mostCurrent._curs.Close();
 }else {
 //BA.debugLineNum = 327;BA.debugLine="tbnGalAver.Checked = False";
mostCurrent._tbngalaver.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _tbnalleg_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 503;BA.debugLine="Sub tbnAlleg_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 504;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 505;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 508;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Allegian";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Allegiance")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 510;BA.debugLine="Q = \"DROP TABLE Allegiance\"";
mostCurrent._q = "DROP TABLE Allegiance";
 //BA.debugLineNum = 511;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 512;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static String  _tbncomgrp_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Sub tbnComGrp_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 406;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 407;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 410;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Commodit";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"CommodityGroups")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 412;BA.debugLine="Q = \"DROP TABLE CommodityGroups\"";
mostCurrent._q = "DROP TABLE CommodityGroups";
 //BA.debugLineNum = 413;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 414;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _tbncommod_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 419;BA.debugLine="Sub tbnCommod_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 420;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 421;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 424;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Commodit";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Commodities")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 426;BA.debugLine="Q = \"DROP TABLE Commodities\"";
mostCurrent._q = "DROP TABLE Commodities";
 //BA.debugLineNum = 427;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 428;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 431;BA.debugLine="End Sub";
return "";
}
public static String  _tbncurrloc_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 545;BA.debugLine="Sub tbnCurrLoc_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 546;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 547;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 550;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Location";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Location")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 552;BA.debugLine="Q = \"DROP TABLE Location\"";
mostCurrent._q = "DROP TABLE Location";
 //BA.debugLineNum = 553;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 554;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 557;BA.debugLine="End Sub";
return "";
}
public static String  _tbnecon_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 489;BA.debugLine="Sub tbnEcon_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 490;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 491;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 494;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Economie";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Economies")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 496;BA.debugLine="Q = \"DROP TABLE Economies\"";
mostCurrent._q = "DROP TABLE Economies";
 //BA.debugLineNum = 497;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 498;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 501;BA.debugLine="End Sub";
return "";
}
public static String  _tbnfactions_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 363;BA.debugLine="Sub tbnFactions_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 364;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 365;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 368;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Factions";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Factions")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 370;BA.debugLine="Q = \"DROP TABLE Factions\"";
mostCurrent._q = "DROP TABLE Factions";
 //BA.debugLineNum = 371;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 372;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return "";
}
public static String  _tbngalaver_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 573;BA.debugLine="Sub tbnGalAver_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 574;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 575;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 578;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Galactic";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"GalacticPrices")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 580;BA.debugLine="Q = \"DROP TABLE GalacticPrices\"";
mostCurrent._q = "DROP TABLE GalacticPrices";
 //BA.debugLineNum = 581;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 582;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 585;BA.debugLine="End Sub";
return "";
}
public static String  _tbngov_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 475;BA.debugLine="Sub tbnGov_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 476;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 477;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 480;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Governme";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Governments")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 482;BA.debugLine="Q = \"DROP TABLE Governments\"";
mostCurrent._q = "DROP TABLE Governments";
 //BA.debugLineNum = 483;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 484;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 487;BA.debugLine="End Sub";
return "";
}
public static String  _tbnmaxly_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 559;BA.debugLine="Sub tbnMaxLY_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 560;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 561;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 564;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"MaxLYTra";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"MaxLYTradeDistance")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 566;BA.debugLine="Q = \"DROP TABLE MaxLYTradeDistance\"";
mostCurrent._q = "DROP TABLE MaxLYTradeDistance";
 //BA.debugLineNum = 567;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 568;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 571;BA.debugLine="End Sub";
return "";
}
public static String  _tbnpopu_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 531;BA.debugLine="Sub tbnPopu_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 532;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 533;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 536;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Populati";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Population")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 538;BA.debugLine="Q = \"DROP TABLE Population\"";
mostCurrent._q = "DROP TABLE Population";
 //BA.debugLineNum = 539;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 540;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 543;BA.debugLine="End Sub";
return "";
}
public static String  _tbnrares_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 433;BA.debugLine="Sub tbnRares_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 434;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 435;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 438;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"RareComm";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"RareCommodities")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 440;BA.debugLine="Q = \"DROP TABLE RareCommodities\"";
mostCurrent._q = "DROP TABLE RareCommodities";
 //BA.debugLineNum = 441;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 442;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 445;BA.debugLine="End Sub";
return "";
}
public static String  _tbnstations_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 349;BA.debugLine="Sub tbnStations_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 350;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 351;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 354;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Stations";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Stations")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 356;BA.debugLine="Q = \"DROP TABLE Stations\"";
mostCurrent._q = "DROP TABLE Stations";
 //BA.debugLineNum = 357;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 358;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 361;BA.debugLine="End Sub";
return "";
}
public static String  _tbnstattyp_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 517;BA.debugLine="Sub tbnStatTyp_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 518;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 519;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 522;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StationT";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StationTypes")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 524;BA.debugLine="Q = \"DROP TABLE StationTypes\"";
mostCurrent._q = "DROP TABLE StationTypes";
 //BA.debugLineNum = 525;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 526;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 529;BA.debugLine="End Sub";
return "";
}
public static String  _tbnstklvl_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 461;BA.debugLine="Sub tbnStkLvl_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 462;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 463;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 466;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StockLev";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockLevels")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 468;BA.debugLine="Q = \"DROP TABLE StockLevels\"";
mostCurrent._q = "DROP TABLE StockLevels";
 //BA.debugLineNum = 469;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 470;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 473;BA.debugLine="End Sub";
return "";
}
public static String  _tbnstktyp_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 447;BA.debugLine="Sub tbnStkTyp_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 448;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 449;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 452;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StockTyp";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockTypes")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 454;BA.debugLine="Q = \"DROP TABLE StockTypes\"";
mostCurrent._q = "DROP TABLE StockTypes";
 //BA.debugLineNum = 455;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 456;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
return "";
}
public static String  _tbnstock_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 391;BA.debugLine="Sub tbnStock_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 392;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 393;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 396;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"StockMar";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StockMarket")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 398;BA.debugLine="Q = \"DROP TABLE StockMarket\"";
mostCurrent._q = "DROP TABLE StockMarket";
 //BA.debugLineNum = 399;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 400;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 403;BA.debugLine="End Sub";
return "";
}
public static String  _tbnsystems_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 335;BA.debugLine="Sub tbnSystems_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 336;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 337;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 340;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"Systems\"";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Systems")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 342;BA.debugLine="Q = \"DROP TABLE Systems\"";
mostCurrent._q = "DROP TABLE Systems";
 //BA.debugLineNum = 343;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 344;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public static String  _tbntempstat_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 377;BA.debugLine="Sub tbnTempStat_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 378;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 379;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 382;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"TempStat";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"TempStations")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 384;BA.debugLine="Q = \"DROP TABLE TempStations\"";
mostCurrent._q = "DROP TABLE TempStations";
 //BA.debugLineNum = 385;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 386;BA.debugLine="RefreshButtons";
_refreshbuttons();
 };
 //BA.debugLineNum = 389;BA.debugLine="End Sub";
return "";
}
}
