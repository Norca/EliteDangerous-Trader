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

public class stationsmaint extends Activity implements B4AActivity{
	public static stationsmaint mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.stationsmaint");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (stationsmaint).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.stationsmaint");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.stationsmaint", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (stationsmaint) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (stationsmaint) Resume **");
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
		return stationsmaint.class;
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
        BA.LogInfo("** Activity (stationsmaint) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (stationsmaint) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _alphaliststations = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadd = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncancel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndelete = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsave = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtstationname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtdistarrpnt = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnstationtype = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnblack = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon8 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon16 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon32 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon64 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon128 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon256 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon512 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon1024 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon2048 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon4096 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon8192 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon16384 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon32768 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon65536 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon131072 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon262144 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbnecon524288 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvstationlist = null;
public static boolean _addoredit = false;
public static boolean _result = false;
public static int _resultnum = 0;
public static String _oldstattypedesc = "";
public static String _q = "";
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.dbadmin _dbadmin = null;
public b4a.example.galaxymaint _galaxymaint = null;
public b4a.example.systemsmaint _systemsmaint = null;
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
 //BA.debugLineNum = 63;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="Activity.LoadLayout(\"StationsMaint\")";
mostCurrent._activity.LoadLayout("StationsMaint",mostCurrent.activityBA);
 //BA.debugLineNum = 66;BA.debugLine="lblTitle.Text = \"Stations for \" & Starter.CurrLoc";
mostCurrent._lbltitle.setText((Object)("Stations for "+mostCurrent._starter._currlocation+" system"));
 //BA.debugLineNum = 68;BA.debugLine="InitSpinners";
_initspinners();
 //BA.debugLineNum = 70;BA.debugLine="Functions.SetColours(Activity)";
mostCurrent._functions._setcolours(mostCurrent.activityBA,mostCurrent._activity);
 //BA.debugLineNum = 72;BA.debugLine="InitEconButtons";
_initeconbuttons();
 //BA.debugLineNum = 74;BA.debugLine="FillStationsList";
_fillstationslist();
 //BA.debugLineNum = 76;BA.debugLine="ButtonReset(False)";
_buttonreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 78;BA.debugLine="StatusReset(False)";
_statusreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 80;BA.debugLine="NavEdit(True)";
_navedit(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _btnadd_click() throws Exception{
 //BA.debugLineNum = 512;BA.debugLine="Sub btnAdd_Click";
 //BA.debugLineNum = 513;BA.debugLine="AddOREdit = True 'Add new record";
_addoredit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 515;BA.debugLine="ButtonReset(True)";
_buttonreset(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 517;BA.debugLine="NavEdit(False)";
_navedit(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 519;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 503;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 504;BA.debugLine="ButtonReset(False)";
_buttonreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 506;BA.debugLine="StatusReset(False)";
_statusreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 508;BA.debugLine="NavEdit(True)";
_navedit(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 510;BA.debugLine="End Sub";
return "";
}
public static String  _btndelete_click() throws Exception{
int _answ = 0;
anywheresoftware.b4a.objects.collections.Map _mp = null;
 //BA.debugLineNum = 477;BA.debugLine="Sub btnDelete_Click";
 //BA.debugLineNum = 478;BA.debugLine="Dim Answ As Int";
_answ = 0;
 //BA.debugLineNum = 480;BA.debugLine="Answ = Msgbox2(\"Do you really want to delete this";
_answ = anywheresoftware.b4a.keywords.Common.Msgbox2("Do you really want to delete this Station and associated market info ?","DELETE record","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 482;BA.debugLine="If Answ = DialogResponse.POSITIVE Then";
if (_answ==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 484;BA.debugLine="Q = \"DELETE FROM StockMarket WHERE StationName =";
mostCurrent._q = "DELETE FROM StockMarket WHERE StationName = ?";
 //BA.debugLineNum = 485;BA.debugLine="Starter.SQLExec.ExecNonQuery2(Q, Array As String";
mostCurrent._starter._sqlexec.ExecNonQuery2(mostCurrent._q,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._edtstationname.getText().toUpperCase()}));
 //BA.debugLineNum = 487;BA.debugLine="Dim mp As Map";
_mp = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 488;BA.debugLine="mp.Initialize";
_mp.Initialize();
 //BA.debugLineNum = 489;BA.debugLine="mp.Put(\"StationName\", edtStationName.Text.ToUppe";
_mp.Put((Object)("StationName"),(Object)(mostCurrent._edtstationname.getText().toUpperCase()));
 //BA.debugLineNum = 490;BA.debugLine="DBUtils.DeleteRecord(Starter.SQLExec, \"Stations\"";
mostCurrent._dbutils._deleterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Stations",_mp);
 //BA.debugLineNum = 492;BA.debugLine="FillStationsList";
_fillstationslist();
 };
 //BA.debugLineNum = 495;BA.debugLine="ButtonReset(False)";
_buttonreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 497;BA.debugLine="StatusReset(False)";
_statusreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 499;BA.debugLine="NavEdit(True)";
_navedit(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 501;BA.debugLine="End Sub";
return "";
}
public static String  _btnexit_click() throws Exception{
 //BA.debugLineNum = 472;BA.debugLine="Sub btnExit_Click";
 //BA.debugLineNum = 473;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 475;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
int _counter = 0;
anywheresoftware.b4a.objects.collections.Map _record = null;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 391;BA.debugLine="Sub btnSave_Click";
 //BA.debugLineNum = 392;BA.debugLine="Dim counter As Int";
_counter = 0;
 //BA.debugLineNum = 393;BA.debugLine="Dim record As Map, whereclause As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 394;BA.debugLine="If AddOREdit = True Then";
if (_addoredit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 396;BA.debugLine="If edtStationName.Text.Length > 0 Then";
if (mostCurrent._edtstationname.getText().length()>0) { 
 //BA.debugLineNum = 397;BA.debugLine="If Functions.StationExists(edtStationName.Text)";
if (mostCurrent._functions._stationexists(mostCurrent.activityBA,mostCurrent._edtstationname.getText())==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 398;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 399;BA.debugLine="record.Put(\"StationName\", edtStationName.Text.";
_record.Put((Object)("StationName"),(Object)(mostCurrent._edtstationname.getText().toUpperCase()));
 //BA.debugLineNum = 400;BA.debugLine="record.Put(\"SystemName\", Starter.CurrLocation)";
_record.Put((Object)("SystemName"),(Object)(mostCurrent._starter._currlocation));
 //BA.debugLineNum = 401;BA.debugLine="record.Put(\"StatTypeDesc\", spnStationType.Sele";
_record.Put((Object)("StatTypeDesc"),(Object)(mostCurrent._spnstationtype.getSelectedItem()));
 //BA.debugLineNum = 402;BA.debugLine="record.Put(\"EconomyNum\", CombineEconomy)";
_record.Put((Object)("EconomyNum"),(Object)(_combineeconomy()));
 //BA.debugLineNum = 403;BA.debugLine="record.Put(\"BlackMarketAvailable\", tbnBlack.Ch";
_record.Put((Object)("BlackMarketAvailable"),(Object)(mostCurrent._tbnblack.getChecked()));
 //BA.debugLineNum = 404;BA.debugLine="record.Put(\"ArrivalPoint\", edtDistArrPnt.Text)";
_record.Put((Object)("ArrivalPoint"),(Object)(mostCurrent._edtdistarrpnt.getText()));
 //BA.debugLineNum = 405;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"Stat";
mostCurrent._sqlutils._table_insertmap(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Stations",_record);
 //BA.debugLineNum = 407;BA.debugLine="If spnStationType.SelectedItem <> \"Not Set\" Th";
if ((mostCurrent._spnstationtype.getSelectedItem()).equals("Not Set") == false) { 
 //BA.debugLineNum = 408;BA.debugLine="Q = \"SELECT NumOfStations FROM StationTypes W";
mostCurrent._q = "SELECT NumOfStations FROM StationTypes WHERE StatTypeDesc = ?";
 //BA.debugLineNum = 409;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._spnstationtype.getSelectedItem()})));
 //BA.debugLineNum = 411;BA.debugLine="counter = counter + 1";
_counter = (int) (_counter+1);
 //BA.debugLineNum = 413;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 414;BA.debugLine="whereclause.Put(\"StatTypeDesc\", spnStationTyp";
_whereclause.Put((Object)("StatTypeDesc"),(Object)(mostCurrent._spnstationtype.getSelectedItem()));
 //BA.debugLineNum = 415;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Station";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StationTypes","NumOfStations",(Object)(_counter),_whereclause);
 };
 };
 //BA.debugLineNum = 418;BA.debugLine="result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 420;BA.debugLine="Msgbox(\"Must enter a name for the Station befor";
anywheresoftware.b4a.keywords.Common.Msgbox("Must enter a name for the Station before save is possible","A T T E N T I O N",mostCurrent.activityBA);
 //BA.debugLineNum = 421;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 }else {
 //BA.debugLineNum = 425;BA.debugLine="If edtStationName.Text.Length > 0 Then";
if (mostCurrent._edtstationname.getText().length()>0) { 
 //BA.debugLineNum = 427;BA.debugLine="If oldStatTypeDesc <> spnStationType.SelectedI";
if ((mostCurrent._oldstattypedesc).equals(mostCurrent._spnstationtype.getSelectedItem()) == false && (mostCurrent._oldstattypedesc).equals("Not Set") == false) { 
 //BA.debugLineNum = 429;BA.debugLine="Q = \"SELECT NumOfStations FROM StationTypes W";
mostCurrent._q = "SELECT NumOfStations FROM StationTypes WHERE StatTypeDesc = ?";
 //BA.debugLineNum = 430;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._oldstattypedesc})));
 //BA.debugLineNum = 431;BA.debugLine="counter = counter - 1";
_counter = (int) (_counter-1);
 //BA.debugLineNum = 432;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 433;BA.debugLine="whereclause.Put(\"StatTypeDesc\", oldStatTypeDe";
_whereclause.Put((Object)("StatTypeDesc"),(Object)(mostCurrent._oldstattypedesc));
 //BA.debugLineNum = 434;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Station";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StationTypes","NumOfStations",(Object)(_counter),_whereclause);
 //BA.debugLineNum = 436;BA.debugLine="Q = \"SELECT NumOfStations FROM StationTypes W";
mostCurrent._q = "SELECT NumOfStations FROM StationTypes WHERE StatTypeDesc = ?";
 //BA.debugLineNum = 437;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._spnstationtype.getSelectedItem()})));
 //BA.debugLineNum = 438;BA.debugLine="counter = counter + 1";
_counter = (int) (_counter+1);
 //BA.debugLineNum = 439;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 440;BA.debugLine="whereclause.Put(\"StatTypeDesc\", spnStationTyp";
_whereclause.Put((Object)("StatTypeDesc"),(Object)(mostCurrent._spnstationtype.getSelectedItem()));
 //BA.debugLineNum = 441;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Station";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"StationTypes","NumOfStations",(Object)(_counter),_whereclause);
 };
 //BA.debugLineNum = 443;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 444;BA.debugLine="whereclause.Put(\"StationName\", edtStationName.";
_whereclause.Put((Object)("StationName"),(Object)(mostCurrent._edtstationname.getText().toUpperCase()));
 //BA.debugLineNum = 445;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 446;BA.debugLine="record.Put(\"StatTypeDesc\", spnStationType.Sele";
_record.Put((Object)("StatTypeDesc"),(Object)(mostCurrent._spnstationtype.getSelectedItem()));
 //BA.debugLineNum = 447;BA.debugLine="record.Put(\"EconomyNum\", CombineEconomy)";
_record.Put((Object)("EconomyNum"),(Object)(_combineeconomy()));
 //BA.debugLineNum = 448;BA.debugLine="record.Put(\"BlackMarketAvailable\", tbnBlack.Ch";
_record.Put((Object)("BlackMarketAvailable"),(Object)(mostCurrent._tbnblack.getChecked()));
 //BA.debugLineNum = 449;BA.debugLine="record.Put(\"ArrivalPoint\", edtDistArrPnt.Text)";
_record.Put((Object)("ArrivalPoint"),(Object)(mostCurrent._edtdistarrpnt.getText()));
 //BA.debugLineNum = 450;BA.debugLine="DBUtils.UpdateRecord2(Starter.SQLExec,\"Station";
mostCurrent._dbutils._updaterecord2(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Stations",_record,_whereclause);
 //BA.debugLineNum = 452;BA.debugLine="result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 454;BA.debugLine="Msgbox(\"Station name can not be blank\", \"A T T";
anywheresoftware.b4a.keywords.Common.Msgbox("Station name can not be blank","A T T E N T I O N",mostCurrent.activityBA);
 //BA.debugLineNum = 455;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 //BA.debugLineNum = 459;BA.debugLine="If result = True Then";
if (_result==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 461;BA.debugLine="ButtonReset(False)";
_buttonreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 463;BA.debugLine="StatusReset(False)";
_statusreset(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 465;BA.debugLine="NavEdit(True)";
_navedit(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 467;BA.debugLine="FillStationsList";
_fillstationslist();
 };
 //BA.debugLineNum = 470;BA.debugLine="End Sub";
return "";
}
public static String  _buttonreset(boolean _status) throws Exception{
 //BA.debugLineNum = 785;BA.debugLine="Sub ButtonReset(Status As Boolean)";
 //BA.debugLineNum = 786;BA.debugLine="If Status = True Then";
if (_status==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 787;BA.debugLine="edtStationName.Enabled = True";
mostCurrent._edtstationname.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 788;BA.debugLine="edtDistArrPnt.Enabled = True";
mostCurrent._edtdistarrpnt.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 789;BA.debugLine="spnStationType.Enabled = True";
mostCurrent._spnstationtype.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 790;BA.debugLine="tbnBlack.Enabled = True";
mostCurrent._tbnblack.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 791;BA.debugLine="tbnEcon1.Enabled = True";
mostCurrent._tbnecon1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 792;BA.debugLine="tbnEcon2.Enabled = True";
mostCurrent._tbnecon2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 793;BA.debugLine="tbnEcon4.Enabled = True";
mostCurrent._tbnecon4.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 794;BA.debugLine="tbnEcon8.Enabled = True";
mostCurrent._tbnecon8.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 795;BA.debugLine="tbnEcon16.Enabled = True";
mostCurrent._tbnecon16.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 796;BA.debugLine="tbnEcon32.Enabled = True";
mostCurrent._tbnecon32.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 797;BA.debugLine="tbnEcon64.Enabled = True";
mostCurrent._tbnecon64.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 798;BA.debugLine="tbnEcon128.Enabled = True";
mostCurrent._tbnecon128.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 799;BA.debugLine="tbnEcon256.Enabled = True";
mostCurrent._tbnecon256.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 800;BA.debugLine="tbnEcon512.Enabled = True";
mostCurrent._tbnecon512.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 801;BA.debugLine="tbnEcon1024.Enabled = True";
mostCurrent._tbnecon1024.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 802;BA.debugLine="tbnEcon2048.Enabled = True";
mostCurrent._tbnecon2048.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 803;BA.debugLine="tbnEcon4096.Enabled = True";
mostCurrent._tbnecon4096.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 804;BA.debugLine="tbnEcon8192.Enabled = True";
mostCurrent._tbnecon8192.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 805;BA.debugLine="tbnEcon16384.Enabled = True";
mostCurrent._tbnecon16384.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 806;BA.debugLine="tbnEcon32768.Enabled = True";
mostCurrent._tbnecon32768.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 807;BA.debugLine="tbnEcon65536.Enabled = True";
mostCurrent._tbnecon65536.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 808;BA.debugLine="tbnEcon131072.Enabled = True";
mostCurrent._tbnecon131072.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 809;BA.debugLine="tbnEcon262144.Enabled = True";
mostCurrent._tbnecon262144.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 810;BA.debugLine="tbnEcon524288.Enabled = True";
mostCurrent._tbnecon524288.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 812;BA.debugLine="edtStationName.Enabled = False";
mostCurrent._edtstationname.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 813;BA.debugLine="edtDistArrPnt.Enabled = False";
mostCurrent._edtdistarrpnt.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 814;BA.debugLine="spnStationType.Enabled = False";
mostCurrent._spnstationtype.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 815;BA.debugLine="tbnBlack.Enabled = False";
mostCurrent._tbnblack.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 816;BA.debugLine="tbnEcon1.Enabled = False";
mostCurrent._tbnecon1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 817;BA.debugLine="tbnEcon2.Enabled = False";
mostCurrent._tbnecon2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 818;BA.debugLine="tbnEcon4.Enabled = False";
mostCurrent._tbnecon4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 819;BA.debugLine="tbnEcon8.Enabled = False";
mostCurrent._tbnecon8.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 820;BA.debugLine="tbnEcon16.Enabled = False";
mostCurrent._tbnecon16.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 821;BA.debugLine="tbnEcon32.Enabled = False";
mostCurrent._tbnecon32.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 822;BA.debugLine="tbnEcon64.Enabled = False";
mostCurrent._tbnecon64.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 823;BA.debugLine="tbnEcon128.Enabled = False";
mostCurrent._tbnecon128.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 824;BA.debugLine="tbnEcon256.Enabled = False";
mostCurrent._tbnecon256.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 825;BA.debugLine="tbnEcon512.Enabled = False";
mostCurrent._tbnecon512.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 826;BA.debugLine="tbnEcon1024.Enabled = False";
mostCurrent._tbnecon1024.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 827;BA.debugLine="tbnEcon2048.Enabled = False";
mostCurrent._tbnecon2048.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 828;BA.debugLine="tbnEcon4096.Enabled = False";
mostCurrent._tbnecon4096.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 829;BA.debugLine="tbnEcon8192.Enabled = False";
mostCurrent._tbnecon8192.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 830;BA.debugLine="tbnEcon16384.Enabled = False";
mostCurrent._tbnecon16384.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 831;BA.debugLine="tbnEcon32768.Enabled = False";
mostCurrent._tbnecon32768.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 832;BA.debugLine="tbnEcon65536.Enabled = False";
mostCurrent._tbnecon65536.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 833;BA.debugLine="tbnEcon131072.Enabled = False";
mostCurrent._tbnecon131072.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 834;BA.debugLine="tbnEcon262144.Enabled = False";
mostCurrent._tbnecon262144.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 835;BA.debugLine="tbnEcon524288.Enabled = False";
mostCurrent._tbnecon524288.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 837;BA.debugLine="End Sub";
return "";
}
public static int  _combineeconomy() throws Exception{
 //BA.debugLineNum = 633;BA.debugLine="Sub CombineEconomy As Int";
 //BA.debugLineNum = 634;BA.debugLine="resultnum = 0";
_resultnum = (int) (0);
 //BA.debugLineNum = 636;BA.debugLine="If tbnEcon524288.Checked = True Then";
if (mostCurrent._tbnecon524288.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 637;BA.debugLine="resultnum = resultnum + 524288";
_resultnum = (int) (_resultnum+524288);
 };
 //BA.debugLineNum = 639;BA.debugLine="If tbnEcon262144.Checked = True Then";
if (mostCurrent._tbnecon262144.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 640;BA.debugLine="resultnum = resultnum + 262144";
_resultnum = (int) (_resultnum+262144);
 };
 //BA.debugLineNum = 642;BA.debugLine="If tbnEcon131072.Checked = True Then";
if (mostCurrent._tbnecon131072.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 643;BA.debugLine="resultnum = resultnum + 131072";
_resultnum = (int) (_resultnum+131072);
 };
 //BA.debugLineNum = 645;BA.debugLine="If tbnEcon65536.Checked = True Then";
if (mostCurrent._tbnecon65536.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 646;BA.debugLine="resultnum = resultnum + 65536";
_resultnum = (int) (_resultnum+65536);
 };
 //BA.debugLineNum = 648;BA.debugLine="If tbnEcon32768.Checked = True Then";
if (mostCurrent._tbnecon32768.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 649;BA.debugLine="resultnum = resultnum + 32768";
_resultnum = (int) (_resultnum+32768);
 };
 //BA.debugLineNum = 651;BA.debugLine="If tbnEcon16384.Checked = True Then";
if (mostCurrent._tbnecon16384.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 652;BA.debugLine="resultnum = resultnum + 16384";
_resultnum = (int) (_resultnum+16384);
 };
 //BA.debugLineNum = 654;BA.debugLine="If tbnEcon8192.Checked = True Then";
if (mostCurrent._tbnecon8192.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 655;BA.debugLine="resultnum = resultnum + 8192";
_resultnum = (int) (_resultnum+8192);
 };
 //BA.debugLineNum = 657;BA.debugLine="If tbnEcon4096.Checked = True Then";
if (mostCurrent._tbnecon4096.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 658;BA.debugLine="resultnum = resultnum + 4096";
_resultnum = (int) (_resultnum+4096);
 };
 //BA.debugLineNum = 660;BA.debugLine="If tbnEcon2048.Checked = True Then";
if (mostCurrent._tbnecon2048.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 661;BA.debugLine="resultnum = resultnum + 2048";
_resultnum = (int) (_resultnum+2048);
 };
 //BA.debugLineNum = 663;BA.debugLine="If tbnEcon1024.Checked = True Then";
if (mostCurrent._tbnecon1024.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 664;BA.debugLine="resultnum = resultnum + 1024";
_resultnum = (int) (_resultnum+1024);
 };
 //BA.debugLineNum = 666;BA.debugLine="If tbnEcon512.Checked = True Then";
if (mostCurrent._tbnecon512.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 667;BA.debugLine="resultnum = resultnum + 512";
_resultnum = (int) (_resultnum+512);
 };
 //BA.debugLineNum = 669;BA.debugLine="If tbnEcon256.Checked = True Then";
if (mostCurrent._tbnecon256.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 670;BA.debugLine="resultnum = resultnum + 256";
_resultnum = (int) (_resultnum+256);
 };
 //BA.debugLineNum = 672;BA.debugLine="If tbnEcon128.Checked = True Then";
if (mostCurrent._tbnecon128.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 673;BA.debugLine="resultnum = resultnum + 128";
_resultnum = (int) (_resultnum+128);
 };
 //BA.debugLineNum = 675;BA.debugLine="If tbnEcon64.Checked = True Then";
if (mostCurrent._tbnecon64.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 676;BA.debugLine="resultnum = resultnum + 64";
_resultnum = (int) (_resultnum+64);
 };
 //BA.debugLineNum = 678;BA.debugLine="If tbnEcon32.Checked = True Then";
if (mostCurrent._tbnecon32.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 679;BA.debugLine="resultnum = resultnum + 32";
_resultnum = (int) (_resultnum+32);
 };
 //BA.debugLineNum = 681;BA.debugLine="If tbnEcon16.Checked = True Then";
if (mostCurrent._tbnecon16.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 682;BA.debugLine="resultnum = resultnum + 16";
_resultnum = (int) (_resultnum+16);
 };
 //BA.debugLineNum = 684;BA.debugLine="If tbnEcon8.Checked = True Then";
if (mostCurrent._tbnecon8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 685;BA.debugLine="resultnum = resultnum + 8";
_resultnum = (int) (_resultnum+8);
 };
 //BA.debugLineNum = 687;BA.debugLine="If tbnEcon4.Checked = True Then";
if (mostCurrent._tbnecon4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 688;BA.debugLine="resultnum = resultnum + 4";
_resultnum = (int) (_resultnum+4);
 };
 //BA.debugLineNum = 690;BA.debugLine="If tbnEcon2.Checked = True Then";
if (mostCurrent._tbnecon2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 691;BA.debugLine="resultnum = resultnum + 2";
_resultnum = (int) (_resultnum+2);
 };
 //BA.debugLineNum = 693;BA.debugLine="If tbnEcon1.Checked = True Then";
if (mostCurrent._tbnecon1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 694;BA.debugLine="resultnum = resultnum + 1";
_resultnum = (int) (_resultnum+1);
 };
 //BA.debugLineNum = 697;BA.debugLine="Return resultnum";
if (true) return _resultnum;
 //BA.debugLineNum = 699;BA.debugLine="End Sub";
return 0;
}
public static String  _edtstationname_focuschanged(boolean _hasfocus) throws Exception{
 //BA.debugLineNum = 525;BA.debugLine="Sub edtStationName_FocusChanged (HasFocus As Boole";
 //BA.debugLineNum = 526;BA.debugLine="If HasFocus = False Then";
if (_hasfocus==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 527;BA.debugLine="edtStationName.Text = edtStationName.Text.ToUppe";
mostCurrent._edtstationname.setText((Object)(mostCurrent._edtstationname.getText().toUpperCase()));
 };
 //BA.debugLineNum = 529;BA.debugLine="End Sub";
return "";
}
public static String  _expandstations() throws Exception{
int _i = 0;
int _econint = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystem = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstations = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.Map _record = null;
 //BA.debugLineNum = 94;BA.debugLine="Sub ExpandStations";
 //BA.debugLineNum = 95;BA.debugLine="Dim i As Int, EconInt As Int";
_i = 0;
_econint = 0;
 //BA.debugLineNum = 96;BA.debugLine="Dim CursSystem As Cursor, CursStations As Cursor";
_curssystem = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursstations = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Dim record As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 100;BA.debugLine="Q = \"DELETE FROM TempStations\"";
mostCurrent._q = "DELETE FROM TempStations";
 //BA.debugLineNum = 101;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 //BA.debugLineNum = 103;BA.debugLine="Q = \"SELECT SystemName FROM Systems WHERE SystemN";
mostCurrent._q = "SELECT SystemName FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 104;BA.debugLine="CursSystem = Starter.SQLExec.ExecQuery2(Q, Array";
_curssystem.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 105;BA.debugLine="If CursSystem.RowCount > 0 Then";
if (_curssystem.getRowCount()>0) { 
 //BA.debugLineNum = 106;BA.debugLine="CursSystem.Position = 0";
_curssystem.setPosition((int) (0));
 //BA.debugLineNum = 107;BA.debugLine="Q = \"SELECT * FROM Stations WHERE SystemName = ?";
mostCurrent._q = "SELECT * FROM Stations WHERE SystemName = ?";
 //BA.debugLineNum = 108;BA.debugLine="CursStations = Starter.SQLExec.ExecQuery2(Q, Arr";
_cursstations.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 109;BA.debugLine="If CursStations.RowCount > 0 Then";
if (_cursstations.getRowCount()>0) { 
 //BA.debugLineNum = 110;BA.debugLine="For i = 0 To CursStations.RowCount - 1";
{
final int step14 = 1;
final int limit14 = (int) (_cursstations.getRowCount()-1);
for (_i = (int) (0) ; (step14 > 0 && _i <= limit14) || (step14 < 0 && _i >= limit14); _i = ((int)(0 + _i + step14)) ) {
 //BA.debugLineNum = 111;BA.debugLine="CursStations.Position = i";
_cursstations.setPosition(_i);
 //BA.debugLineNum = 112;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 113;BA.debugLine="EconInt = CursStations.GetInt(\"EconomyNum\")";
_econint = _cursstations.GetInt("EconomyNum");
 //BA.debugLineNum = 115;BA.debugLine="If EconInt > 524287 Then";
if (_econint>524287) { 
 //BA.debugLineNum = 116;BA.debugLine="sb.Append(Elite.FindEconomy(524288))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (524288)));
 //BA.debugLineNum = 117;BA.debugLine="EconInt = EconInt - 524288";
_econint = (int) (_econint-524288);
 };
 //BA.debugLineNum = 119;BA.debugLine="If EconInt > 262143 Then";
if (_econint>262143) { 
 //BA.debugLineNum = 120;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 121;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 123;BA.debugLine="sb.Append(Elite.FindEconomy(262144))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (262144)));
 //BA.debugLineNum = 124;BA.debugLine="EconInt = EconInt - 262144";
_econint = (int) (_econint-262144);
 };
 //BA.debugLineNum = 126;BA.debugLine="If EconInt > 131071 Then";
if (_econint>131071) { 
 //BA.debugLineNum = 127;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 128;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 130;BA.debugLine="sb.Append(Elite.FindEconomy(131072))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (131072)));
 //BA.debugLineNum = 131;BA.debugLine="EconInt = EconInt - 131072";
_econint = (int) (_econint-131072);
 };
 //BA.debugLineNum = 133;BA.debugLine="If EconInt > 65535 Then";
if (_econint>65535) { 
 //BA.debugLineNum = 134;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 135;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 137;BA.debugLine="sb.Append(Elite.FindEconomy(65536))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (65536)));
 //BA.debugLineNum = 138;BA.debugLine="EconInt = EconInt - 65536";
_econint = (int) (_econint-65536);
 };
 //BA.debugLineNum = 140;BA.debugLine="If EconInt > 32767 Then";
if (_econint>32767) { 
 //BA.debugLineNum = 141;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 142;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 144;BA.debugLine="sb.Append(Elite.FindEconomy(32768))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (32768)));
 //BA.debugLineNum = 145;BA.debugLine="EconInt = EconInt - 32768";
_econint = (int) (_econint-32768);
 };
 //BA.debugLineNum = 147;BA.debugLine="If EconInt > 16383 Then";
if (_econint>16383) { 
 //BA.debugLineNum = 148;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 149;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 151;BA.debugLine="sb.Append(Elite.FindEconomy(16384))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (16384)));
 //BA.debugLineNum = 152;BA.debugLine="EconInt = EconInt - 16384";
_econint = (int) (_econint-16384);
 };
 //BA.debugLineNum = 154;BA.debugLine="If EconInt > 8191 Then";
if (_econint>8191) { 
 //BA.debugLineNum = 155;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 156;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 158;BA.debugLine="sb.Append(Elite.FindEconomy(8192))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (8192)));
 //BA.debugLineNum = 159;BA.debugLine="EconInt = EconInt - 8192";
_econint = (int) (_econint-8192);
 };
 //BA.debugLineNum = 161;BA.debugLine="If EconInt > 4095 Then";
if (_econint>4095) { 
 //BA.debugLineNum = 162;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 163;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 165;BA.debugLine="sb.Append(Elite.FindEconomy(4096))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (4096)));
 //BA.debugLineNum = 166;BA.debugLine="EconInt = EconInt - 4096";
_econint = (int) (_econint-4096);
 };
 //BA.debugLineNum = 168;BA.debugLine="If EconInt > 2047 Then";
if (_econint>2047) { 
 //BA.debugLineNum = 169;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 170;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 172;BA.debugLine="sb.Append(Elite.FindEconomy(2048))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (2048)));
 //BA.debugLineNum = 173;BA.debugLine="EconInt = EconInt - 2048";
_econint = (int) (_econint-2048);
 };
 //BA.debugLineNum = 175;BA.debugLine="If EconInt > 1023 Then";
if (_econint>1023) { 
 //BA.debugLineNum = 176;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 177;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 179;BA.debugLine="sb.Append(Elite.FindEconomy(1024))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (1024)));
 //BA.debugLineNum = 180;BA.debugLine="EconInt = EconInt - 1024";
_econint = (int) (_econint-1024);
 };
 //BA.debugLineNum = 182;BA.debugLine="If EconInt > 511 Then";
if (_econint>511) { 
 //BA.debugLineNum = 183;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 184;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 186;BA.debugLine="sb.Append(Elite.FindEconomy(512))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (512)));
 //BA.debugLineNum = 187;BA.debugLine="EconInt = EconInt - 512";
_econint = (int) (_econint-512);
 };
 //BA.debugLineNum = 189;BA.debugLine="If EconInt > 255 Then";
if (_econint>255) { 
 //BA.debugLineNum = 190;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 191;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 193;BA.debugLine="sb.Append(Elite.FindEconomy(256))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (256)));
 //BA.debugLineNum = 194;BA.debugLine="EconInt = EconInt - 256";
_econint = (int) (_econint-256);
 };
 //BA.debugLineNum = 196;BA.debugLine="If EconInt > 127 Then";
if (_econint>127) { 
 //BA.debugLineNum = 197;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 198;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 200;BA.debugLine="sb.Append(Elite.FindEconomy(128))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (128)));
 //BA.debugLineNum = 201;BA.debugLine="EconInt = EconInt - 128";
_econint = (int) (_econint-128);
 };
 //BA.debugLineNum = 203;BA.debugLine="If EconInt > 63 Then";
if (_econint>63) { 
 //BA.debugLineNum = 204;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 205;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 207;BA.debugLine="sb.Append(Elite.FindEconomy(64))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (64)));
 //BA.debugLineNum = 208;BA.debugLine="EconInt = EconInt - 64";
_econint = (int) (_econint-64);
 };
 //BA.debugLineNum = 210;BA.debugLine="If EconInt > 31 Then";
if (_econint>31) { 
 //BA.debugLineNum = 211;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 212;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 214;BA.debugLine="sb.Append(Elite.FindEconomy(32))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (32)));
 //BA.debugLineNum = 215;BA.debugLine="EconInt = EconInt - 32";
_econint = (int) (_econint-32);
 };
 //BA.debugLineNum = 217;BA.debugLine="If EconInt > 15 Then";
if (_econint>15) { 
 //BA.debugLineNum = 218;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 219;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 221;BA.debugLine="sb.Append(Elite.FindEconomy(16))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (16)));
 //BA.debugLineNum = 222;BA.debugLine="EconInt = EconInt - 16";
_econint = (int) (_econint-16);
 };
 //BA.debugLineNum = 224;BA.debugLine="If EconInt > 7 Then";
if (_econint>7) { 
 //BA.debugLineNum = 225;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 226;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 228;BA.debugLine="sb.Append(Elite.FindEconomy(8))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (8)));
 //BA.debugLineNum = 229;BA.debugLine="EconInt = EconInt - 8";
_econint = (int) (_econint-8);
 };
 //BA.debugLineNum = 231;BA.debugLine="If EconInt > 3 Then";
if (_econint>3) { 
 //BA.debugLineNum = 232;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 233;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 235;BA.debugLine="sb.Append(Elite.FindEconomy(4))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (4)));
 //BA.debugLineNum = 236;BA.debugLine="EconInt = EconInt - 4";
_econint = (int) (_econint-4);
 };
 //BA.debugLineNum = 238;BA.debugLine="If EconInt > 1 Then";
if (_econint>1) { 
 //BA.debugLineNum = 239;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 240;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 242;BA.debugLine="sb.Append(Elite.FindEconomy(2))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (2)));
 //BA.debugLineNum = 243;BA.debugLine="EconInt = EconInt - 2";
_econint = (int) (_econint-2);
 };
 //BA.debugLineNum = 245;BA.debugLine="If EconInt > 0 Then";
if (_econint>0) { 
 //BA.debugLineNum = 246;BA.debugLine="If sb.Length > 0 Then";
if (_sb.getLength()>0) { 
 //BA.debugLineNum = 247;BA.debugLine="sb.Append(\", \")";
_sb.Append(", ");
 };
 //BA.debugLineNum = 249;BA.debugLine="sb.Append(Elite.FindEconomy(1))";
_sb.Append(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int) (1)));
 //BA.debugLineNum = 250;BA.debugLine="EconInt = EconInt - 1";
_econint = (int) (_econint-1);
 };
 //BA.debugLineNum = 253;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 254;BA.debugLine="record.Put(\"StationName\", CursStations.GetStri";
_record.Put((Object)("StationName"),(Object)(_cursstations.GetString("StationName")));
 //BA.debugLineNum = 255;BA.debugLine="record.Put(\"SystemName\", CursStations.GetStrin";
_record.Put((Object)("SystemName"),(Object)(_cursstations.GetString("SystemName")));
 //BA.debugLineNum = 256;BA.debugLine="record.Put(\"StatTypeDesc\", CursStations.GetStr";
_record.Put((Object)("StatTypeDesc"),(Object)(_cursstations.GetString("StatTypeDesc")));
 //BA.debugLineNum = 257;BA.debugLine="record.Put(\"PopulationSize\", CursStations.GetS";
_record.Put((Object)("PopulationSize"),(Object)(_cursstations.GetString("PopulationSize")));
 //BA.debugLineNum = 258;BA.debugLine="record.Put(\"EconomyNum\", CursStations.GetInt(\"";
_record.Put((Object)("EconomyNum"),(Object)(_cursstations.GetInt("EconomyNum")));
 //BA.debugLineNum = 259;BA.debugLine="record.Put(\"ECDesc\", sb.ToString)";
_record.Put((Object)("ECDesc"),(Object)(_sb.ToString()));
 //BA.debugLineNum = 260;BA.debugLine="record.Put(\"BlackMarketAvailable\", CursStation";
_record.Put((Object)("BlackMarketAvailable"),(Object)(_cursstations.GetInt("BlackMarketAvailable")));
 //BA.debugLineNum = 261;BA.debugLine="record.Put(\"ArrivalPoint\", CursStations.GetDou";
_record.Put((Object)("ArrivalPoint"),(Object)(_cursstations.GetDouble("ArrivalPoint")));
 //BA.debugLineNum = 262;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"Temp";
mostCurrent._sqlutils._table_insertmap(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"TempStations",_record);
 }
};
 };
 //BA.debugLineNum = 265;BA.debugLine="CursStations.Close";
_cursstations.Close();
 };
 //BA.debugLineNum = 267;BA.debugLine="CursSystem.Close";
_curssystem.Close();
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return "";
}
public static String  _fillstationslist() throws Exception{
 //BA.debugLineNum = 376;BA.debugLine="Sub FillStationsList";
 //BA.debugLineNum = 378;BA.debugLine="ExpandStations";
_expandstations();
 //BA.debugLineNum = 380;BA.debugLine="Q = \"SELECT TS.StationName AS [Station], TS.ECDes";
mostCurrent._q = "SELECT TS.StationName AS [Station], TS.ECDesc AS [Economy], TS.StatTypeDesc AS [Type], CASE TS.BlackMarketAvailable WHEN 0 THEN 'False' WHEN 1 THEN 'True' END As [Black Market], TS.ArrivalPoint AS [Arrival Point] FROM TempStations TS ORDER BY TS.ArrivalPoint ASC";
 //BA.debugLineNum = 381;BA.debugLine="wbvStationList.LoadHtml(DBUtils.ExecuteHtml(Start";
mostCurrent._wbvstationlist.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0),anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 383;BA.debugLine="StationsListAlpha 'Sets the ID lookup list of the";
_stationslistalpha();
 //BA.debugLineNum = 385;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private btnAdd As Button";
mostCurrent._btnadd = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnCancel As Button";
mostCurrent._btncancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnDelete As Button";
mostCurrent._btndelete = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnSave As Button";
mostCurrent._btnsave = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private edtStationName As EditText";
mostCurrent._edtstationname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private edtDistArrPnt As EditText";
mostCurrent._edtdistarrpnt = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblTitle As Label";
mostCurrent._lbltitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private spnStationType As Spinner";
mostCurrent._spnstationtype = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private tbnBlack As ToggleButton";
mostCurrent._tbnblack = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private tbnEcon1 As ToggleButton";
mostCurrent._tbnecon1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private tbnEcon2 As ToggleButton";
mostCurrent._tbnecon2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private tbnEcon4 As ToggleButton";
mostCurrent._tbnecon4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private tbnEcon8 As ToggleButton";
mostCurrent._tbnecon8 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private tbnEcon16 As ToggleButton";
mostCurrent._tbnecon16 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private tbnEcon32 As ToggleButton";
mostCurrent._tbnecon32 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private tbnEcon64 As ToggleButton";
mostCurrent._tbnecon64 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private tbnEcon128 As ToggleButton";
mostCurrent._tbnecon128 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private tbnEcon256 As ToggleButton";
mostCurrent._tbnecon256 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private tbnEcon512 As ToggleButton";
mostCurrent._tbnecon512 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private tbnEcon1024 As ToggleButton";
mostCurrent._tbnecon1024 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private tbnEcon2048 As ToggleButton";
mostCurrent._tbnecon2048 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private tbnEcon4096 As ToggleButton";
mostCurrent._tbnecon4096 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private tbnEcon8192 As ToggleButton";
mostCurrent._tbnecon8192 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private tbnEcon16384 As ToggleButton";
mostCurrent._tbnecon16384 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private tbnEcon32768 As ToggleButton";
mostCurrent._tbnecon32768 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private tbnEcon65536 As ToggleButton";
mostCurrent._tbnecon65536 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private tbnEcon131072 As ToggleButton";
mostCurrent._tbnecon131072 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private tbnEcon262144 As ToggleButton";
mostCurrent._tbnecon262144 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private tbnEcon524288 As ToggleButton";
mostCurrent._tbnecon524288 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private wbvStationList As WebView";
mostCurrent._wbvstationlist = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim AddOREdit As Boolean 'True equals Add, False";
_addoredit = false;
 //BA.debugLineNum = 54;BA.debugLine="Dim result As Boolean";
_result = false;
 //BA.debugLineNum = 56;BA.debugLine="Dim resultnum As Int";
_resultnum = 0;
 //BA.debugLineNum = 57;BA.debugLine="Dim oldStatTypeDesc As String";
mostCurrent._oldstattypedesc = "";
 //BA.debugLineNum = 59;BA.debugLine="Dim Q As String";
mostCurrent._q = "";
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _initeconbuttons() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curseconomies = null;
 //BA.debugLineNum = 291;BA.debugLine="Sub InitEconButtons";
 //BA.debugLineNum = 292;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 293;BA.debugLine="Dim CursEconomies As Cursor";
_curseconomies = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 296;BA.debugLine="Q = \"SELECT * FROM Economies ORDER BY EconomyID A";
mostCurrent._q = "SELECT * FROM Economies ORDER BY EconomyID ASC";
 //BA.debugLineNum = 297;BA.debugLine="CursEconomies = Starter.SQLExec.ExecQuery(Q)";
_curseconomies.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 298;BA.debugLine="If CursEconomies.RowCount > 0 Then";
if (_curseconomies.getRowCount()>0) { 
 //BA.debugLineNum = 299;BA.debugLine="For i = 0 To CursEconomies.RowCount - 1";
{
final int step6 = 1;
final int limit6 = (int) (_curseconomies.getRowCount()-1);
for (_i = (int) (0) ; (step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6); _i = ((int)(0 + _i + step6)) ) {
 //BA.debugLineNum = 300;BA.debugLine="CursEconomies.Position = i";
_curseconomies.setPosition(_i);
 //BA.debugLineNum = 301;BA.debugLine="Select True";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.True,_curseconomies.GetInt("EconomyID")==1,_curseconomies.GetInt("EconomyID")==2,_curseconomies.GetInt("EconomyID")==4,_curseconomies.GetInt("EconomyID")==8,_curseconomies.GetInt("EconomyID")==16,_curseconomies.GetInt("EconomyID")==32,_curseconomies.GetInt("EconomyID")==64,_curseconomies.GetInt("EconomyID")==128,_curseconomies.GetInt("EconomyID")==256,_curseconomies.GetInt("EconomyID")==512,_curseconomies.GetInt("EconomyID")==1024,_curseconomies.GetInt("EconomyID")==2048,_curseconomies.GetInt("EconomyID")==4096,_curseconomies.GetInt("EconomyID")==8192,_curseconomies.GetInt("EconomyID")==16384,_curseconomies.GetInt("EconomyID")==32768,_curseconomies.GetInt("EconomyID")==65536,_curseconomies.GetInt("EconomyID")==131072,_curseconomies.GetInt("EconomyID")==262144,_curseconomies.GetInt("EconomyID")==524288)) {
case 0: {
 //BA.debugLineNum = 303;BA.debugLine="tbnEcon1.TextOff = CursEconomies.GetString(\"E";
mostCurrent._tbnecon1.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 304;BA.debugLine="tbnEcon1.TextOn = CursEconomies.GetString(\"Ec";
mostCurrent._tbnecon1.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 1: {
 //BA.debugLineNum = 306;BA.debugLine="tbnEcon2.TextOff = CursEconomies.GetString(\"E";
mostCurrent._tbnecon2.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 307;BA.debugLine="tbnEcon2.TextOn = CursEconomies.GetString(\"Ec";
mostCurrent._tbnecon2.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 2: {
 //BA.debugLineNum = 309;BA.debugLine="tbnEcon4.TextOff = CursEconomies.GetString(\"E";
mostCurrent._tbnecon4.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 310;BA.debugLine="tbnEcon4.TextOn = CursEconomies.GetString(\"Ec";
mostCurrent._tbnecon4.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 3: {
 //BA.debugLineNum = 312;BA.debugLine="tbnEcon8.TextOff = CursEconomies.GetString(\"E";
mostCurrent._tbnecon8.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 313;BA.debugLine="tbnEcon8.TextOn = CursEconomies.GetString(\"Ec";
mostCurrent._tbnecon8.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 4: {
 //BA.debugLineNum = 315;BA.debugLine="tbnEcon16.TextOff = CursEconomies.GetString(\"";
mostCurrent._tbnecon16.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 316;BA.debugLine="tbnEcon16.TextOn = CursEconomies.GetString(\"E";
mostCurrent._tbnecon16.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 5: {
 //BA.debugLineNum = 318;BA.debugLine="tbnEcon32.TextOff = CursEconomies.GetString(\"";
mostCurrent._tbnecon32.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 319;BA.debugLine="tbnEcon32.TextOn = CursEconomies.GetString(\"E";
mostCurrent._tbnecon32.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 6: {
 //BA.debugLineNum = 321;BA.debugLine="tbnEcon64.TextOff = CursEconomies.GetString(\"";
mostCurrent._tbnecon64.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 322;BA.debugLine="tbnEcon64.TextOn = CursEconomies.GetString(\"E";
mostCurrent._tbnecon64.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 7: {
 //BA.debugLineNum = 324;BA.debugLine="tbnEcon128.TextOff = CursEconomies.GetString(";
mostCurrent._tbnecon128.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 325;BA.debugLine="tbnEcon128.TextOn = CursEconomies.GetString(\"";
mostCurrent._tbnecon128.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 8: {
 //BA.debugLineNum = 327;BA.debugLine="tbnEcon256.TextOff = CursEconomies.GetString(";
mostCurrent._tbnecon256.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 328;BA.debugLine="tbnEcon256.TextOn = CursEconomies.GetString(\"";
mostCurrent._tbnecon256.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 9: {
 //BA.debugLineNum = 330;BA.debugLine="tbnEcon512.TextOff = CursEconomies.GetString(";
mostCurrent._tbnecon512.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 331;BA.debugLine="tbnEcon512.TextOn = CursEconomies.GetString(\"";
mostCurrent._tbnecon512.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 10: {
 //BA.debugLineNum = 333;BA.debugLine="tbnEcon1024.TextOff = CursEconomies.GetString";
mostCurrent._tbnecon1024.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 334;BA.debugLine="tbnEcon1024.TextOn = CursEconomies.GetString(";
mostCurrent._tbnecon1024.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 11: {
 //BA.debugLineNum = 336;BA.debugLine="tbnEcon2048.TextOff = CursEconomies.GetString";
mostCurrent._tbnecon2048.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 337;BA.debugLine="tbnEcon2048.TextOn = CursEconomies.GetString(";
mostCurrent._tbnecon2048.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 12: {
 //BA.debugLineNum = 339;BA.debugLine="tbnEcon4096.TextOff = CursEconomies.GetString";
mostCurrent._tbnecon4096.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 340;BA.debugLine="tbnEcon4096.TextOn = CursEconomies.GetString(";
mostCurrent._tbnecon4096.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 13: {
 //BA.debugLineNum = 342;BA.debugLine="tbnEcon8192.TextOff = CursEconomies.GetString";
mostCurrent._tbnecon8192.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 343;BA.debugLine="tbnEcon8192.TextOn = CursEconomies.GetString(";
mostCurrent._tbnecon8192.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 14: {
 //BA.debugLineNum = 345;BA.debugLine="tbnEcon16384.TextOff = CursEconomies.GetStrin";
mostCurrent._tbnecon16384.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 346;BA.debugLine="tbnEcon16384.TextOn = CursEconomies.GetString";
mostCurrent._tbnecon16384.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 15: {
 //BA.debugLineNum = 348;BA.debugLine="tbnEcon32768.TextOff = CursEconomies.GetStrin";
mostCurrent._tbnecon32768.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 349;BA.debugLine="tbnEcon32768.TextOn = CursEconomies.GetString";
mostCurrent._tbnecon32768.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 16: {
 //BA.debugLineNum = 351;BA.debugLine="tbnEcon65536.TextOff = CursEconomies.GetStrin";
mostCurrent._tbnecon65536.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 352;BA.debugLine="tbnEcon65536.TextOn = CursEconomies.GetString";
mostCurrent._tbnecon65536.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 17: {
 //BA.debugLineNum = 354;BA.debugLine="tbnEcon131072.TextOff = CursEconomies.GetStri";
mostCurrent._tbnecon131072.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 355;BA.debugLine="tbnEcon131072.TextOn = CursEconomies.GetStrin";
mostCurrent._tbnecon131072.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 18: {
 //BA.debugLineNum = 357;BA.debugLine="tbnEcon262144.TextOff = CursEconomies.GetStri";
mostCurrent._tbnecon262144.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 358;BA.debugLine="tbnEcon262144.TextOn = CursEconomies.GetStrin";
mostCurrent._tbnecon262144.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
case 19: {
 //BA.debugLineNum = 360;BA.debugLine="tbnEcon524288.TextOff = CursEconomies.GetStri";
mostCurrent._tbnecon524288.setTextOff(_curseconomies.GetString("EconomyDesc"));
 //BA.debugLineNum = 361;BA.debugLine="tbnEcon524288.TextOn = CursEconomies.GetStrin";
mostCurrent._tbnecon524288.setTextOn(_curseconomies.GetString("EconomyDesc").toUpperCase());
 break; }
}
;
 }
};
 };
 //BA.debugLineNum = 365;BA.debugLine="CursEconomies.Close";
_curseconomies.Close();
 //BA.debugLineNum = 367;BA.debugLine="End Sub";
return "";
}
public static String  _initspinners() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstationtypes = null;
 //BA.debugLineNum = 271;BA.debugLine="Sub InitSpinners";
 //BA.debugLineNum = 272;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 273;BA.debugLine="Dim CursStationTypes As Cursor";
_cursstationtypes = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 276;BA.debugLine="Q = \"SELECT * FROM StationTypes ORDER BY NumOfSta";
mostCurrent._q = "SELECT * FROM StationTypes ORDER BY NumOfStations DESC";
 //BA.debugLineNum = 277;BA.debugLine="CursStationTypes = Starter.SQLExec.ExecQuery(Q)";
_cursstationtypes.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 278;BA.debugLine="If CursStationTypes.RowCount > 0 Then";
if (_cursstationtypes.getRowCount()>0) { 
 //BA.debugLineNum = 279;BA.debugLine="spnStationType.Clear";
mostCurrent._spnstationtype.Clear();
 //BA.debugLineNum = 280;BA.debugLine="spnStationType.Add(\"Not Set\")";
mostCurrent._spnstationtype.Add("Not Set");
 //BA.debugLineNum = 281;BA.debugLine="For i = 0 To CursStationTypes.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (_cursstationtypes.getRowCount()-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 282;BA.debugLine="CursStationTypes.Position = i";
_cursstationtypes.setPosition(_i);
 //BA.debugLineNum = 283;BA.debugLine="spnStationType.Add(CursStationTypes.GetString(\"";
mostCurrent._spnstationtype.Add(_cursstationtypes.GetString("StatTypeDesc"));
 }
};
 };
 //BA.debugLineNum = 286;BA.debugLine="spnStationType.SelectedIndex = 0";
mostCurrent._spnstationtype.setSelectedIndex((int) (0));
 //BA.debugLineNum = 287;BA.debugLine="CursStationTypes.Close";
_cursstationtypes.Close();
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _navedit(boolean _status) throws Exception{
 //BA.debugLineNum = 870;BA.debugLine="Sub NavEdit(Status As Boolean)";
 //BA.debugLineNum = 871;BA.debugLine="If Status = True Then";
if (_status==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 872;BA.debugLine="btnAdd.Visible = True";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 873;BA.debugLine="btnCancel.Visible = False";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 874;BA.debugLine="btnDelete.Visible = False";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 875;BA.debugLine="btnSave.Visible = False";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 877;BA.debugLine="btnAdd.Visible = False";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 878;BA.debugLine="btnCancel.Visible = True";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 879;BA.debugLine="btnDelete.Visible = True";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 880;BA.debugLine="btnSave.Visible = True";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 882;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim alphalistStations As List";
_alphaliststations = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _spliteconomy(int _econint) throws Exception{
 //BA.debugLineNum = 701;BA.debugLine="Sub SplitEconomy(EconInt As Int)";
 //BA.debugLineNum = 702;BA.debugLine="If EconInt > 524287 Then";
if (_econint>524287) { 
 //BA.debugLineNum = 703;BA.debugLine="tbnEcon524288.Checked = True";
mostCurrent._tbnecon524288.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 704;BA.debugLine="EconInt = EconInt - 524288";
_econint = (int) (_econint-524288);
 };
 //BA.debugLineNum = 706;BA.debugLine="If EconInt > 262143 Then";
if (_econint>262143) { 
 //BA.debugLineNum = 707;BA.debugLine="tbnEcon262144.Checked = True";
mostCurrent._tbnecon262144.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 708;BA.debugLine="EconInt = EconInt - 262144";
_econint = (int) (_econint-262144);
 };
 //BA.debugLineNum = 710;BA.debugLine="If EconInt > 131071 Then";
if (_econint>131071) { 
 //BA.debugLineNum = 711;BA.debugLine="tbnEcon131072.Checked = True";
mostCurrent._tbnecon131072.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 712;BA.debugLine="EconInt = EconInt - 131072";
_econint = (int) (_econint-131072);
 };
 //BA.debugLineNum = 714;BA.debugLine="If EconInt > 65535 Then";
if (_econint>65535) { 
 //BA.debugLineNum = 715;BA.debugLine="tbnEcon65536.Checked = True";
mostCurrent._tbnecon65536.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 716;BA.debugLine="EconInt = EconInt - 65536";
_econint = (int) (_econint-65536);
 };
 //BA.debugLineNum = 718;BA.debugLine="If EconInt > 32767 Then";
if (_econint>32767) { 
 //BA.debugLineNum = 719;BA.debugLine="tbnEcon32768.Checked = True";
mostCurrent._tbnecon32768.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 720;BA.debugLine="EconInt = EconInt - 32768";
_econint = (int) (_econint-32768);
 };
 //BA.debugLineNum = 722;BA.debugLine="If EconInt > 16383 Then";
if (_econint>16383) { 
 //BA.debugLineNum = 723;BA.debugLine="tbnEcon16384.Checked = True";
mostCurrent._tbnecon16384.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 724;BA.debugLine="EconInt = EconInt - 16384";
_econint = (int) (_econint-16384);
 };
 //BA.debugLineNum = 726;BA.debugLine="If EconInt > 8191 Then";
if (_econint>8191) { 
 //BA.debugLineNum = 727;BA.debugLine="tbnEcon8192.Checked = True";
mostCurrent._tbnecon8192.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 728;BA.debugLine="EconInt = EconInt - 8192";
_econint = (int) (_econint-8192);
 };
 //BA.debugLineNum = 730;BA.debugLine="If EconInt > 4095 Then";
if (_econint>4095) { 
 //BA.debugLineNum = 731;BA.debugLine="tbnEcon4096.Checked = True";
mostCurrent._tbnecon4096.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 732;BA.debugLine="EconInt = EconInt - 4096";
_econint = (int) (_econint-4096);
 };
 //BA.debugLineNum = 734;BA.debugLine="If EconInt > 2047 Then";
if (_econint>2047) { 
 //BA.debugLineNum = 735;BA.debugLine="tbnEcon2048.Checked = True";
mostCurrent._tbnecon2048.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 736;BA.debugLine="EconInt = EconInt - 2048";
_econint = (int) (_econint-2048);
 };
 //BA.debugLineNum = 738;BA.debugLine="If EconInt > 1023 Then";
if (_econint>1023) { 
 //BA.debugLineNum = 739;BA.debugLine="tbnEcon1024.Checked = True";
mostCurrent._tbnecon1024.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 740;BA.debugLine="EconInt = EconInt - 1024";
_econint = (int) (_econint-1024);
 };
 //BA.debugLineNum = 742;BA.debugLine="If EconInt > 511 Then";
if (_econint>511) { 
 //BA.debugLineNum = 743;BA.debugLine="tbnEcon512.Checked = True";
mostCurrent._tbnecon512.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 744;BA.debugLine="EconInt = EconInt - 512";
_econint = (int) (_econint-512);
 };
 //BA.debugLineNum = 746;BA.debugLine="If EconInt > 255 Then";
if (_econint>255) { 
 //BA.debugLineNum = 747;BA.debugLine="tbnEcon256.Checked = True";
mostCurrent._tbnecon256.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 748;BA.debugLine="EconInt = EconInt - 256";
_econint = (int) (_econint-256);
 };
 //BA.debugLineNum = 750;BA.debugLine="If EconInt > 127 Then";
if (_econint>127) { 
 //BA.debugLineNum = 751;BA.debugLine="tbnEcon128.Checked = True";
mostCurrent._tbnecon128.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 752;BA.debugLine="EconInt = EconInt - 128";
_econint = (int) (_econint-128);
 };
 //BA.debugLineNum = 754;BA.debugLine="If EconInt > 63 Then";
if (_econint>63) { 
 //BA.debugLineNum = 755;BA.debugLine="tbnEcon64.Checked = True";
mostCurrent._tbnecon64.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 756;BA.debugLine="EconInt = EconInt - 64";
_econint = (int) (_econint-64);
 };
 //BA.debugLineNum = 758;BA.debugLine="If EconInt > 31 Then";
if (_econint>31) { 
 //BA.debugLineNum = 759;BA.debugLine="tbnEcon32.Checked = True";
mostCurrent._tbnecon32.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 760;BA.debugLine="EconInt = EconInt - 32";
_econint = (int) (_econint-32);
 };
 //BA.debugLineNum = 762;BA.debugLine="If EconInt > 15 Then";
if (_econint>15) { 
 //BA.debugLineNum = 763;BA.debugLine="tbnEcon16.Checked = True";
mostCurrent._tbnecon16.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 764;BA.debugLine="EconInt = EconInt - 16";
_econint = (int) (_econint-16);
 };
 //BA.debugLineNum = 766;BA.debugLine="If EconInt > 7 Then";
if (_econint>7) { 
 //BA.debugLineNum = 767;BA.debugLine="tbnEcon8.Checked = True";
mostCurrent._tbnecon8.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 768;BA.debugLine="EconInt = EconInt - 8";
_econint = (int) (_econint-8);
 };
 //BA.debugLineNum = 770;BA.debugLine="If EconInt > 3 Then";
if (_econint>3) { 
 //BA.debugLineNum = 771;BA.debugLine="tbnEcon4.Checked = True";
mostCurrent._tbnecon4.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 772;BA.debugLine="EconInt = EconInt - 4";
_econint = (int) (_econint-4);
 };
 //BA.debugLineNum = 774;BA.debugLine="If EconInt > 1 Then";
if (_econint>1) { 
 //BA.debugLineNum = 775;BA.debugLine="tbnEcon2.Checked = True";
mostCurrent._tbnecon2.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 776;BA.debugLine="EconInt = EconInt - 2";
_econint = (int) (_econint-2);
 };
 //BA.debugLineNum = 778;BA.debugLine="If EconInt > 0 Then";
if (_econint>0) { 
 //BA.debugLineNum = 779;BA.debugLine="tbnEcon1.Checked = True";
mostCurrent._tbnecon1.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 780;BA.debugLine="EconInt = EconInt - 1";
_econint = (int) (_econint-1);
 };
 //BA.debugLineNum = 783;BA.debugLine="End Sub";
return "";
}
public static String  _spnstationtype_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 531;BA.debugLine="Sub spnStationType_ItemClick (Position As Int, Val";
 //BA.debugLineNum = 532;BA.debugLine="If Value = \"Settlement\" Or Value = \"Surface Port\"";
if ((_value).equals((Object)("Settlement")) || (_value).equals((Object)("Surface Port"))) { 
 //BA.debugLineNum = 534;BA.debugLine="tbnEcon1.Enabled = False";
mostCurrent._tbnecon1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 535;BA.debugLine="tbnEcon4.Enabled = False";
mostCurrent._tbnecon4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 536;BA.debugLine="tbnEcon16.Enabled = False";
mostCurrent._tbnecon16.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 537;BA.debugLine="tbnEcon64.Enabled = False";
mostCurrent._tbnecon64.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 538;BA.debugLine="tbnEcon256.Enabled = False";
mostCurrent._tbnecon256.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 539;BA.debugLine="tbnEcon1024.Enabled = False";
mostCurrent._tbnecon1024.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 540;BA.debugLine="tbnEcon4096.Enabled = False";
mostCurrent._tbnecon4096.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 541;BA.debugLine="tbnEcon16384.Enabled = False";
mostCurrent._tbnecon16384.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 542;BA.debugLine="tbnEcon65536.Enabled = False";
mostCurrent._tbnecon65536.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 543;BA.debugLine="tbnEcon262144.Enabled = False";
mostCurrent._tbnecon262144.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 545;BA.debugLine="tbnEcon2.Enabled = True";
mostCurrent._tbnecon2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 546;BA.debugLine="tbnEcon8.Enabled = True";
mostCurrent._tbnecon8.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 547;BA.debugLine="tbnEcon32.Enabled = True";
mostCurrent._tbnecon32.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 548;BA.debugLine="tbnEcon128.Enabled = True";
mostCurrent._tbnecon128.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 549;BA.debugLine="tbnEcon512.Enabled = True";
mostCurrent._tbnecon512.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 550;BA.debugLine="tbnEcon2048.Enabled = True";
mostCurrent._tbnecon2048.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 551;BA.debugLine="tbnEcon8192.Enabled = True";
mostCurrent._tbnecon8192.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 552;BA.debugLine="tbnEcon32768.Enabled = True";
mostCurrent._tbnecon32768.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 553;BA.debugLine="tbnEcon131072.Enabled = True";
mostCurrent._tbnecon131072.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 554;BA.debugLine="tbnEcon524288.Enabled = True";
mostCurrent._tbnecon524288.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 556;BA.debugLine="tbnEcon1.Checked = False";
mostCurrent._tbnecon1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 557;BA.debugLine="tbnEcon4.Checked = False";
mostCurrent._tbnecon4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 558;BA.debugLine="tbnEcon16.Checked = False";
mostCurrent._tbnecon16.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 559;BA.debugLine="tbnEcon64.Checked = False";
mostCurrent._tbnecon64.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 560;BA.debugLine="tbnEcon256.Checked = False";
mostCurrent._tbnecon256.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 561;BA.debugLine="tbnEcon1024.Checked = False";
mostCurrent._tbnecon1024.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 562;BA.debugLine="tbnEcon4096.Checked = False";
mostCurrent._tbnecon4096.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 563;BA.debugLine="tbnEcon16384.Checked = False";
mostCurrent._tbnecon16384.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 564;BA.debugLine="tbnEcon65536.Checked = False";
mostCurrent._tbnecon65536.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 565;BA.debugLine="tbnEcon262144.Checked = False";
mostCurrent._tbnecon262144.setChecked(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 568;BA.debugLine="tbnEcon1.Enabled = True";
mostCurrent._tbnecon1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 569;BA.debugLine="tbnEcon4.Enabled = True";
mostCurrent._tbnecon4.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 570;BA.debugLine="tbnEcon16.Enabled = True";
mostCurrent._tbnecon16.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 571;BA.debugLine="tbnEcon64.Enabled = True";
mostCurrent._tbnecon64.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 572;BA.debugLine="tbnEcon256.Enabled = True";
mostCurrent._tbnecon256.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 573;BA.debugLine="tbnEcon1024.Enabled = True";
mostCurrent._tbnecon1024.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 574;BA.debugLine="tbnEcon4096.Enabled = True";
mostCurrent._tbnecon4096.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 575;BA.debugLine="tbnEcon16384.Enabled = True";
mostCurrent._tbnecon16384.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 576;BA.debugLine="tbnEcon65536.Enabled = True";
mostCurrent._tbnecon65536.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 577;BA.debugLine="tbnEcon262144.Enabled = True";
mostCurrent._tbnecon262144.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 579;BA.debugLine="tbnEcon2.Enabled = False";
mostCurrent._tbnecon2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 580;BA.debugLine="tbnEcon8.Enabled = False";
mostCurrent._tbnecon8.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 581;BA.debugLine="tbnEcon32.Enabled = False";
mostCurrent._tbnecon32.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 582;BA.debugLine="tbnEcon128.Enabled = False";
mostCurrent._tbnecon128.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 583;BA.debugLine="tbnEcon512.Enabled = False";
mostCurrent._tbnecon512.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 584;BA.debugLine="tbnEcon2048.Enabled = False";
mostCurrent._tbnecon2048.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 585;BA.debugLine="tbnEcon8192.Enabled = False";
mostCurrent._tbnecon8192.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 586;BA.debugLine="tbnEcon32768.Enabled = False";
mostCurrent._tbnecon32768.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 587;BA.debugLine="tbnEcon131072.Enabled = False";
mostCurrent._tbnecon131072.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 588;BA.debugLine="tbnEcon524288.Enabled = False";
mostCurrent._tbnecon524288.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 590;BA.debugLine="tbnEcon2.Checked = False";
mostCurrent._tbnecon2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 591;BA.debugLine="tbnEcon8.Checked = False";
mostCurrent._tbnecon8.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 592;BA.debugLine="tbnEcon32.Checked = False";
mostCurrent._tbnecon32.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 593;BA.debugLine="tbnEcon128.Checked = False";
mostCurrent._tbnecon128.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 594;BA.debugLine="tbnEcon512.Checked = False";
mostCurrent._tbnecon512.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 595;BA.debugLine="tbnEcon2048.Checked = False";
mostCurrent._tbnecon2048.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 596;BA.debugLine="tbnEcon8192.Checked = False";
mostCurrent._tbnecon8192.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 597;BA.debugLine="tbnEcon32768.Checked = False";
mostCurrent._tbnecon32768.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 598;BA.debugLine="tbnEcon131072.Checked = False";
mostCurrent._tbnecon131072.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 599;BA.debugLine="tbnEcon524288.Checked = False";
mostCurrent._tbnecon524288.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 601;BA.debugLine="End Sub";
return "";
}
public static String  _stationslistalpha() throws Exception{
 //BA.debugLineNum = 369;BA.debugLine="Sub StationsListAlpha";
 //BA.debugLineNum = 370;BA.debugLine="Q = \"SELECT * FROM TempStations ORDER BY ArrivalP";
mostCurrent._q = "SELECT * FROM TempStations ORDER BY ArrivalPoint ASC";
 //BA.debugLineNum = 372;BA.debugLine="alphalistStations = DBUtils.ExecuteMemoryTable(St";
_alphaliststations = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
public static String  _statusreset(boolean _status) throws Exception{
 //BA.debugLineNum = 839;BA.debugLine="Sub StatusReset(Status As Boolean)";
 //BA.debugLineNum = 840;BA.debugLine="If Status = True Then";
if (_status==anywheresoftware.b4a.keywords.Common.True) { 
 }else {
 //BA.debugLineNum = 843;BA.debugLine="edtStationName.Text = \"\"";
mostCurrent._edtstationname.setText((Object)(""));
 //BA.debugLineNum = 844;BA.debugLine="edtDistArrPnt.Text = \"\"";
mostCurrent._edtdistarrpnt.setText((Object)(""));
 //BA.debugLineNum = 845;BA.debugLine="spnStationType.SelectedIndex = 0";
mostCurrent._spnstationtype.setSelectedIndex((int) (0));
 //BA.debugLineNum = 846;BA.debugLine="tbnBlack.Checked = False";
mostCurrent._tbnblack.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 847;BA.debugLine="tbnEcon1.Checked = False";
mostCurrent._tbnecon1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 848;BA.debugLine="tbnEcon2.Checked = False";
mostCurrent._tbnecon2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 849;BA.debugLine="tbnEcon4.Checked = False";
mostCurrent._tbnecon4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 850;BA.debugLine="tbnEcon8.Checked = False";
mostCurrent._tbnecon8.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 851;BA.debugLine="tbnEcon16.Checked = False";
mostCurrent._tbnecon16.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 852;BA.debugLine="tbnEcon32.Checked = False";
mostCurrent._tbnecon32.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 853;BA.debugLine="tbnEcon64.Checked = False";
mostCurrent._tbnecon64.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 854;BA.debugLine="tbnEcon128.Checked = False";
mostCurrent._tbnecon128.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 855;BA.debugLine="tbnEcon256.Checked = False";
mostCurrent._tbnecon256.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 856;BA.debugLine="tbnEcon512.Checked = False";
mostCurrent._tbnecon512.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 857;BA.debugLine="tbnEcon1024.Checked = False";
mostCurrent._tbnecon1024.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 858;BA.debugLine="tbnEcon2048.Checked = False";
mostCurrent._tbnecon2048.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 859;BA.debugLine="tbnEcon4096.Checked = False";
mostCurrent._tbnecon4096.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 860;BA.debugLine="tbnEcon8192.Checked = False";
mostCurrent._tbnecon8192.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 861;BA.debugLine="tbnEcon16384.Checked = False";
mostCurrent._tbnecon16384.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 862;BA.debugLine="tbnEcon32768.Checked = False";
mostCurrent._tbnecon32768.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 863;BA.debugLine="tbnEcon65536.Checked = False";
mostCurrent._tbnecon65536.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 864;BA.debugLine="tbnEcon131072.Checked = False";
mostCurrent._tbnecon131072.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 865;BA.debugLine="tbnEcon262144.Checked = False";
mostCurrent._tbnecon262144.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 866;BA.debugLine="tbnEcon524288.Checked = False";
mostCurrent._tbnecon524288.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 868;BA.debugLine="End Sub";
return "";
}
public static boolean  _wbvstationlist_overrideurl(String _url) throws Exception{
String[] _values = null;
int _row = 0;
String[] _val = null;
 //BA.debugLineNum = 603;BA.debugLine="Sub wbvStationList_OverrideUrl (Url As String) As";
 //BA.debugLineNum = 605;BA.debugLine="Dim Values() As String";
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 606;BA.debugLine="Dim Row As Int";
_row = 0;
 //BA.debugLineNum = 608;BA.debugLine="Values = Regex.Split(\"[.]\", Url.SubString(7))";
_values = anywheresoftware.b4a.keywords.Common.Regex.Split("[.]",_url.substring((int) (7)));
 //BA.debugLineNum = 609;BA.debugLine="Row = Values(1)";
_row = (int)(Double.parseDouble(_values[(int) (1)]));
 //BA.debugLineNum = 611;BA.debugLine="Dim Val(7) As String";
_val = new String[(int) (7)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 613;BA.debugLine="Val = alphalistStations.Get(Row)";
_val = (String[])(_alphaliststations.Get(_row));
 //BA.debugLineNum = 615;BA.debugLine="edtStationName.Text = Val(0).ToUpperCase";
mostCurrent._edtstationname.setText((Object)(_val[(int) (0)].toUpperCase()));
 //BA.debugLineNum = 616;BA.debugLine="SplitEconomy(Val(4))";
_spliteconomy((int)(Double.parseDouble(_val[(int) (4)])));
 //BA.debugLineNum = 617;BA.debugLine="spnStationType.SelectedIndex = spnStationType.Ind";
mostCurrent._spnstationtype.setSelectedIndex(mostCurrent._spnstationtype.IndexOf(_val[(int) (2)]));
 //BA.debugLineNum = 618;BA.debugLine="tbnBlack.Checked = Functions.IntToBool(Val(6))";
mostCurrent._tbnblack.setChecked(mostCurrent._functions._inttobool(mostCurrent.activityBA,(int)(Double.parseDouble(_val[(int) (6)]))));
 //BA.debugLineNum = 619;BA.debugLine="edtDistArrPnt.Text = Val(7)";
mostCurrent._edtdistarrpnt.setText((Object)(_val[(int) (7)]));
 //BA.debugLineNum = 621;BA.debugLine="oldStatTypeDesc = Val(2)	'Current Station Type De";
mostCurrent._oldstattypedesc = _val[(int) (2)];
 //BA.debugLineNum = 623;BA.debugLine="AddOREdit = False 'Edit existing record";
_addoredit = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 625;BA.debugLine="ButtonReset(True)";
_buttonreset(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 627;BA.debugLine="NavEdit(False)";
_navedit(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 629;BA.debugLine="Return True 'Don't try to navigate to this URL";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 631;BA.debugLine="End Sub";
return false;
}
}
