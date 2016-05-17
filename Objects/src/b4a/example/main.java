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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _supplymarketlist = null;
public static anywheresoftware.b4a.objects.collections.List _demandmarketlist = null;
public static anywheresoftware.b4a.objects.collections.List _raresmarketlist = null;
public static long _dbfilesize = 0L;
public anywheresoftware.b4a.objects.ButtonWrapper _btndbadmin = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsystemsmaint = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnstationsmaint = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnstockmaint = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexport = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btngalaxy = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtmaxdist = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvmarketsupply = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvmarketdemand = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvknownrares = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvtradesystemssup = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvtradesystemsdem = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvtradesystemsnon = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvtradesystemsrares = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnsystem = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnstation = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnnonitems = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnfindsystem = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcurrentsystem = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcurrentstation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsysecon = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatecon = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaxly = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldbsize = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsize = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblanchornum = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltradesup = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltradedem = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltradenon = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltraderares = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldestination = null;
public anywheresoftware.b4a.objects.TabHostWrapper _thmain = null;
public uk.co.martinpearman.b4a.tabhostextras.TabHostExtras _tabmanager = null;
public static String _q = "";
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

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (dbadmin.mostCurrent != null);
vis = vis | (galaxymaint.mostCurrent != null);
vis = vis | (systemsmaint.mostCurrent != null);
vis = vis | (stationsmaint.mostCurrent != null);
vis = vis | (stockmarketmaint.mostCurrent != null);
vis = vis | (splash.mostCurrent != null);
vis = vis | (commodupdate.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp1 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp2 = null;
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 85;BA.debugLine="DBFileSize = (File.Size(File.DirRootExternal, \"El";
_dbfilesize = (long) ((anywheresoftware.b4a.keywords.Common.File.Size(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db")/(double)1024));
 //BA.debugLineNum = 88;BA.debugLine="StartActivity(Splash)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._splash.getObject()));
 //BA.debugLineNum = 91;BA.debugLine="Activity.LoadLayout(\"MainV2\")";
mostCurrent._activity.LoadLayout("MainV2",mostCurrent.activityBA);
 //BA.debugLineNum = 94;BA.debugLine="Dim bmp1, bmp2 As Bitmap";
_bmp1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_bmp2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 95;BA.debugLine="bmp1 = LoadBitmap(File.DirAssets, \"ic.png\")";
_bmp1 = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic.png");
 //BA.debugLineNum = 96;BA.debugLine="bmp2 = LoadBitmap(File.DirAssets, \"ic_selected.pn";
_bmp2 = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_selected.png");
 //BA.debugLineNum = 98;BA.debugLine="thMain.AddTabWithIcon (\"Supply\", bmp1, bmp2, \"Sup";
mostCurrent._thmain.AddTabWithIcon(mostCurrent.activityBA,"Supply",(android.graphics.Bitmap)(_bmp1.getObject()),(android.graphics.Bitmap)(_bmp2.getObject()),"SupplyTab");
 //BA.debugLineNum = 99;BA.debugLine="thMain.AddTab(\"Demand\", \"DemandTab\")";
mostCurrent._thmain.AddTab(mostCurrent.activityBA,"Demand","DemandTab");
 //BA.debugLineNum = 100;BA.debugLine="thMain.AddTab(\"Non Traded\", \"NonTradeTab\")";
mostCurrent._thmain.AddTab(mostCurrent.activityBA,"Non Traded","NonTradeTab");
 //BA.debugLineNum = 101;BA.debugLine="thMain.AddTab(\"Known Rares\", \"RaresTab\")";
mostCurrent._thmain.AddTab(mostCurrent.activityBA,"Known Rares","RaresTab");
 //BA.debugLineNum = 102;BA.debugLine="thMain.AddTab(\"Galactic Prices\", \"GalacticTab\")";
mostCurrent._thmain.AddTab(mostCurrent.activityBA,"Galactic Prices","GalacticTab");
 //BA.debugLineNum = 103;BA.debugLine="thMain.AddTab(\"Search\", \"FindTab\")";
mostCurrent._thmain.AddTab(mostCurrent.activityBA,"Search","FindTab");
 //BA.debugLineNum = 105;BA.debugLine="TabManager.setTabHeight(thMain,35)";
mostCurrent._tabmanager.setTabHeight((android.widget.TabHost)(mostCurrent._thmain.getObject()),(int) (35));
 //BA.debugLineNum = 106;BA.debugLine="TabManager.setTabTextColor(thMain, Starter.TextCo";
mostCurrent._tabmanager.setTabTextColor((android.widget.TabHost)(mostCurrent._thmain.getObject()),mostCurrent._starter._textcolour);
 //BA.debugLineNum = 108;BA.debugLine="EDTables.DatabaseSetup";
mostCurrent._edtables._databasesetup(mostCurrent.activityBA);
 //BA.debugLineNum = 110;BA.debugLine="lblSize.Text = DBFileSize & \" KB\"";
mostCurrent._lblsize.setText((Object)(BA.NumberToString(_dbfilesize)+" KB"));
 //BA.debugLineNum = 111;BA.debugLine="lblAnchorNum.Text = Starter.AnchorsNumber";
mostCurrent._lblanchornum.setText((Object)(mostCurrent._starter._anchorsnumber));
 //BA.debugLineNum = 113;BA.debugLine="Functions.SetColours(Activity)";
mostCurrent._functions._setcolours(mostCurrent.activityBA,mostCurrent._activity);
 //BA.debugLineNum = 115;BA.debugLine="wbvMarketSupply.LoadHtml(\"<html><body>Select Stat";
mostCurrent._wbvmarketsupply.LoadHtml("<html><body>Select Station to view data</body></html>");
 //BA.debugLineNum = 116;BA.debugLine="wbvMarketDemand.LoadHtml(\"<html><body>Select Stat";
mostCurrent._wbvmarketdemand.LoadHtml("<html><body>Select Station to view data</body></html>");
 //BA.debugLineNum = 117;BA.debugLine="wbvKnownRares.LoadHtml(\"<html><body>Select Statio";
mostCurrent._wbvknownrares.LoadHtml("<html><body>Select Station to view data</body></html>");
 //BA.debugLineNum = 118;BA.debugLine="wbvTradeSystemsSup.LoadHtml(\"<html><body>Select C";
mostCurrent._wbvtradesystemssup.LoadHtml("<html><body>Select Commodity to view data</body></html>");
 //BA.debugLineNum = 119;BA.debugLine="wbvTradeSystemsDem.LoadHtml(\"<html><body>Select C";
mostCurrent._wbvtradesystemsdem.LoadHtml("<html><body>Select Commodity to view data</body></html>");
 //BA.debugLineNum = 120;BA.debugLine="wbvTradeSystemsNon.LoadHtml(\"<html><body>Select C";
mostCurrent._wbvtradesystemsnon.LoadHtml("<html><body>Select Commodity to view data</body></html>");
 //BA.debugLineNum = 121;BA.debugLine="wbvTradeSystemsRares.LoadHtml(\"<html><body>Select";
mostCurrent._wbvtradesystemsrares.LoadHtml("<html><body>Select Rare Commodity to view data</body></html>");
 //BA.debugLineNum = 123;BA.debugLine="PopulateCurrLocation";
_populatecurrlocation();
 //BA.debugLineNum = 124;BA.debugLine="LoadCurrLocation";
_loadcurrlocation();
 //BA.debugLineNum = 125;BA.debugLine="LoadCurrStation";
_loadcurrstation();
 //BA.debugLineNum = 126;BA.debugLine="LoadCurrMaxLY";
_loadcurrmaxly();
 //BA.debugLineNum = 128;BA.debugLine="RaresPopulate";
_rarespopulate();
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 133;BA.debugLine="PopulateCurrLocation";
_populatecurrlocation();
 //BA.debugLineNum = 134;BA.debugLine="PopulateAllegTypes";
_populateallegtypes();
 //BA.debugLineNum = 136;BA.debugLine="DBFileSize = (File.Size(File.DirRootExternal, \"El";
_dbfilesize = (long) ((anywheresoftware.b4a.keywords.Common.File.Size(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db")/(double)1024));
 //BA.debugLineNum = 137;BA.debugLine="lblSize.Text = DBFileSize & \" KB\"";
mostCurrent._lblsize.setText((Object)(BA.NumberToString(_dbfilesize)+" KB"));
 //BA.debugLineNum = 138;BA.debugLine="lblAnchorNum.Text = Starter.AnchorsNumber";
mostCurrent._lblanchornum.setText((Object)(mostCurrent._starter._anchorsnumber));
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _btndbadmin_click() throws Exception{
 //BA.debugLineNum = 154;BA.debugLine="Sub btnDBAdmin_Click";
 //BA.debugLineNum = 155;BA.debugLine="StartActivity(\"DBAdmin\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("DBAdmin"));
 //BA.debugLineNum = 156;BA.debugLine="End Sub";
return "";
}
public static String  _btnexit_click() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Sub btnExit_Click";
 //BA.debugLineNum = 151;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _btnexport_click() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstations = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursprices = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableWorkbookWrapper _newworkbook = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableSheetWrapper _sheet1 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableSheetWrapper _sheet2 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableSheetWrapper _sheet3 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell1 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell2 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell3 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell4 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell5 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell6 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell7 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell8 = null;
anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper _cell9 = null;
 //BA.debugLineNum = 174;BA.debugLine="Sub btnExport_Click";
 //BA.debugLineNum = 175;BA.debugLine="ProgressDialogShow(\"Please wait while data is exp";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Please wait while data is exported...");
 //BA.debugLineNum = 176;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 177;BA.debugLine="Dim CursSystems, CursStations, CursPrices As Curs";
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursstations = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursprices = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 178;BA.debugLine="Dim newWorkbook As WritableWorkbook";
_newworkbook = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableWorkbookWrapper();
 //BA.debugLineNum = 179;BA.debugLine="newWorkbook.Initialize(File.DirRootExternal, \"EDP";
_newworkbook.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EDPrices.xls");
 //BA.debugLineNum = 180;BA.debugLine="Dim sheet1 As WritableSheet";
_sheet1 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableSheetWrapper();
 //BA.debugLineNum = 181;BA.debugLine="Dim sheet2 As WritableSheet";
_sheet2 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableSheetWrapper();
 //BA.debugLineNum = 182;BA.debugLine="Dim sheet3 As WritableSheet";
_sheet3 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableSheetWrapper();
 //BA.debugLineNum = 183;BA.debugLine="sheet1 = newWorkbook.AddSheet(\"Systems\", 0)";
_sheet1 = _newworkbook.AddSheet("Systems",(int) (0));
 //BA.debugLineNum = 184;BA.debugLine="sheet2 = newWorkbook.AddSheet(\"Stations\", 1)";
_sheet2 = _newworkbook.AddSheet("Stations",(int) (1));
 //BA.debugLineNum = 185;BA.debugLine="sheet3 = newWorkbook.AddSheet(\"Prices\", 2)";
_sheet3 = _newworkbook.AddSheet("Prices",(int) (2));
 //BA.debugLineNum = 187;BA.debugLine="Q = \"SELECT S.SystemName, S.GovDesc, S.AllegDesc,";
mostCurrent._q = "SELECT S.SystemName, S.GovDesc, S.AllegDesc, S.EconomyID, S.SpaceX, S.SpaceY, S.SpaceZ, S.ExactLocation FROM Systems S ORDER BY S.SystemName ASC";
 //BA.debugLineNum = 188;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery(Q)";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 189;BA.debugLine="If CursSystems.RowCount > 0 Then";
if (_curssystems.getRowCount()>0) { 
 //BA.debugLineNum = 190;BA.debugLine="For i = 0 To CursSystems.RowCount - 1";
{
final int step15 = 1;
final int limit15 = (int) (_curssystems.getRowCount()-1);
for (_i = (int) (0) ; (step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15); _i = ((int)(0 + _i + step15)) ) {
 //BA.debugLineNum = 191;BA.debugLine="CursSystems.Position = i";
_curssystems.setPosition(_i);
 //BA.debugLineNum = 192;BA.debugLine="Dim cell1, cell2, cell3, cell4, cell5, cell6, c";
_cell1 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell2 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell3 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell4 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell5 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell6 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell7 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell8 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell9 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
 //BA.debugLineNum = 193;BA.debugLine="cell1.InitializeText(0, i, CursSystems.GetStrin";
_cell1.InitializeText((int) (0),_i,_curssystems.GetString("SystemName"));
 //BA.debugLineNum = 194;BA.debugLine="cell2.InitializeText(1, i, CursSystems.GetStrin";
_cell2.InitializeText((int) (1),_i,_curssystems.GetString("GovDesc"));
 //BA.debugLineNum = 195;BA.debugLine="cell3.InitializeText(2, i, CursSystems.GetStrin";
_cell3.InitializeText((int) (2),_i,_curssystems.GetString("AllegDesc"));
 //BA.debugLineNum = 196;BA.debugLine="cell4.InitializeNumber(3, i, CursSystems.GetInt";
_cell4.InitializeNumber((int) (3),_i,_curssystems.GetInt("EconomyID"));
 //BA.debugLineNum = 197;BA.debugLine="cell5.InitializeText(4, i, Elite.FindEconomyNam";
_cell5.InitializeText((int) (4),_i,mostCurrent._elite._findeconomynames(mostCurrent.activityBA,_curssystems.GetInt("EconomyID")));
 //BA.debugLineNum = 198;BA.debugLine="cell6.InitializeNumber(5, i, CursSystems.GetDou";
_cell6.InitializeNumber((int) (5),_i,_curssystems.GetDouble("SpaceX"));
 //BA.debugLineNum = 199;BA.debugLine="cell7.InitializeNumber(6, i, CursSystems.GetDou";
_cell7.InitializeNumber((int) (6),_i,_curssystems.GetDouble("SpaceY"));
 //BA.debugLineNum = 200;BA.debugLine="cell8.InitializeNumber(7, i, CursSystems.GetDou";
_cell8.InitializeNumber((int) (7),_i,_curssystems.GetDouble("SpaceZ"));
 //BA.debugLineNum = 201;BA.debugLine="cell9.InitializeNumber(8, i, CursSystems.GetInt";
_cell9.InitializeNumber((int) (8),_i,_curssystems.GetInt("ExactLocation"));
 //BA.debugLineNum = 202;BA.debugLine="sheet1.AddCell(cell1)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell1.getObject()));
 //BA.debugLineNum = 203;BA.debugLine="sheet1.AddCell(cell2)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell2.getObject()));
 //BA.debugLineNum = 204;BA.debugLine="sheet1.AddCell(cell3)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell3.getObject()));
 //BA.debugLineNum = 205;BA.debugLine="sheet1.AddCell(cell4)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell4.getObject()));
 //BA.debugLineNum = 206;BA.debugLine="sheet1.AddCell(cell5)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell5.getObject()));
 //BA.debugLineNum = 207;BA.debugLine="sheet1.AddCell(cell6)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell6.getObject()));
 //BA.debugLineNum = 208;BA.debugLine="sheet1.AddCell(cell7)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell7.getObject()));
 //BA.debugLineNum = 209;BA.debugLine="sheet1.AddCell(cell8)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell8.getObject()));
 //BA.debugLineNum = 210;BA.debugLine="sheet1.AddCell(cell9)";
_sheet1.AddCell((jxl.write.WritableCell)(_cell9.getObject()));
 }
};
 };
 //BA.debugLineNum = 214;BA.debugLine="Q = \"SELECT SystemName, StationName, StatTypeDesc";
mostCurrent._q = "SELECT SystemName, StationName, StatTypeDesc, EconomyNum, BlackMarketAvailable, ArrivalPoint FROM Stations ORDER BY SystemName ASC, StationName ASC";
 //BA.debugLineNum = 215;BA.debugLine="CursStations = Starter.SQLExec.ExecQuery(Q)";
_cursstations.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 216;BA.debugLine="If CursStations.RowCount > 0 Then";
if (_cursstations.getRowCount()>0) { 
 //BA.debugLineNum = 217;BA.debugLine="For i = 0 To CursStations.RowCount - 1";
{
final int step41 = 1;
final int limit41 = (int) (_cursstations.getRowCount()-1);
for (_i = (int) (0) ; (step41 > 0 && _i <= limit41) || (step41 < 0 && _i >= limit41); _i = ((int)(0 + _i + step41)) ) {
 //BA.debugLineNum = 218;BA.debugLine="CursStations.Position = i";
_cursstations.setPosition(_i);
 //BA.debugLineNum = 219;BA.debugLine="Dim cell1, cell2, cell3, cell4, cell5, cell6, c";
_cell1 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell2 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell3 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell4 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell5 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell6 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell7 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
 //BA.debugLineNum = 220;BA.debugLine="cell1.InitializeText(0, i, CursStations.GetStri";
_cell1.InitializeText((int) (0),_i,_cursstations.GetString("SystemName"));
 //BA.debugLineNum = 221;BA.debugLine="cell2.InitializeText(1, i, CursStations.GetStri";
_cell2.InitializeText((int) (1),_i,_cursstations.GetString("StationName"));
 //BA.debugLineNum = 222;BA.debugLine="cell3.InitializeText(2, i, CursStations.GetStri";
_cell3.InitializeText((int) (2),_i,_cursstations.GetString("StatTypeDesc"));
 //BA.debugLineNum = 223;BA.debugLine="cell4.InitializeNumber(3, i, CursStations.GetIn";
_cell4.InitializeNumber((int) (3),_i,_cursstations.GetInt("EconomyNum"));
 //BA.debugLineNum = 224;BA.debugLine="cell5.InitializeText(4, i, Elite.FindEconomyNam";
_cell5.InitializeText((int) (4),_i,mostCurrent._elite._findeconomynames(mostCurrent.activityBA,_cursstations.GetInt("EconomyNum")));
 //BA.debugLineNum = 225;BA.debugLine="cell6.InitializeNumber(5, i, CursStations.GetIn";
_cell6.InitializeNumber((int) (5),_i,_cursstations.GetInt("BlackMarketAvailable"));
 //BA.debugLineNum = 226;BA.debugLine="cell7.InitializeNumber(6, i, CursStations.GetDo";
_cell7.InitializeNumber((int) (6),_i,_cursstations.GetDouble("ArrivalPoint"));
 //BA.debugLineNum = 227;BA.debugLine="sheet2.AddCell(cell1)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell1.getObject()));
 //BA.debugLineNum = 228;BA.debugLine="sheet2.AddCell(cell2)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell2.getObject()));
 //BA.debugLineNum = 229;BA.debugLine="sheet2.AddCell(cell3)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell3.getObject()));
 //BA.debugLineNum = 230;BA.debugLine="sheet2.AddCell(cell4)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell4.getObject()));
 //BA.debugLineNum = 231;BA.debugLine="sheet2.AddCell(cell5)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell5.getObject()));
 //BA.debugLineNum = 232;BA.debugLine="sheet2.AddCell(cell6)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell6.getObject()));
 //BA.debugLineNum = 233;BA.debugLine="sheet2.AddCell(cell7)";
_sheet2.AddCell((jxl.write.WritableCell)(_cell7.getObject()));
 }
};
 };
 //BA.debugLineNum = 237;BA.debugLine="Q = \"SELECT SystemName, StationName, ComGrpDesc,";
mostCurrent._q = "SELECT SystemName, StationName, ComGrpDesc, CommodDesc, StockTypeDesc, StockLevelRank FROM StockMarket ORDER BY SystemName ASC, StationName ASC, ComGrpDesc ASC, CommodDesc ASC";
 //BA.debugLineNum = 238;BA.debugLine="CursPrices = Starter.SQLExec.ExecQuery(Q)";
_cursprices.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 239;BA.debugLine="If CursPrices.RowCount > 0 Then";
if (_cursprices.getRowCount()>0) { 
 //BA.debugLineNum = 240;BA.debugLine="For i = 0 To CursPrices.RowCount - 1";
{
final int step63 = 1;
final int limit63 = (int) (_cursprices.getRowCount()-1);
for (_i = (int) (0) ; (step63 > 0 && _i <= limit63) || (step63 < 0 && _i >= limit63); _i = ((int)(0 + _i + step63)) ) {
 //BA.debugLineNum = 241;BA.debugLine="CursPrices.Position = i";
_cursprices.setPosition(_i);
 //BA.debugLineNum = 242;BA.debugLine="Dim cell1, cell2, cell3, cell4, cell5, cell6 As";
_cell1 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell2 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell3 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell4 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell5 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
_cell6 = new anywheresoftware.b4a.objects.WorkbookWrapper.WritableCellWrapper();
 //BA.debugLineNum = 243;BA.debugLine="cell1.InitializeText(0, i, CursPrices.GetString";
_cell1.InitializeText((int) (0),_i,_cursprices.GetString("SystemName"));
 //BA.debugLineNum = 244;BA.debugLine="cell2.InitializeText(1, i, CursPrices.GetString";
_cell2.InitializeText((int) (1),_i,_cursprices.GetString("StationName"));
 //BA.debugLineNum = 245;BA.debugLine="cell3.InitializeText(2, i, CursPrices.GetString";
_cell3.InitializeText((int) (2),_i,_cursprices.GetString("ComGrpDesc"));
 //BA.debugLineNum = 246;BA.debugLine="cell4.InitializeText(3, i, CursPrices.GetString";
_cell4.InitializeText((int) (3),_i,_cursprices.GetString("CommodDesc"));
 //BA.debugLineNum = 247;BA.debugLine="cell5.InitializeText(4, i, CursPrices.GetString";
_cell5.InitializeText((int) (4),_i,_cursprices.GetString("StockTypeDesc"));
 //BA.debugLineNum = 248;BA.debugLine="cell6.InitializeNumber(5, i, CursPrices.GetInt(";
_cell6.InitializeNumber((int) (5),_i,_cursprices.GetInt("StockLevelRank"));
 //BA.debugLineNum = 249;BA.debugLine="sheet3.AddCell(cell1)";
_sheet3.AddCell((jxl.write.WritableCell)(_cell1.getObject()));
 //BA.debugLineNum = 250;BA.debugLine="sheet3.AddCell(cell2)";
_sheet3.AddCell((jxl.write.WritableCell)(_cell2.getObject()));
 //BA.debugLineNum = 251;BA.debugLine="sheet3.AddCell(cell3)";
_sheet3.AddCell((jxl.write.WritableCell)(_cell3.getObject()));
 //BA.debugLineNum = 252;BA.debugLine="sheet3.AddCell(cell4)";
_sheet3.AddCell((jxl.write.WritableCell)(_cell4.getObject()));
 //BA.debugLineNum = 253;BA.debugLine="sheet3.AddCell(cell5)";
_sheet3.AddCell((jxl.write.WritableCell)(_cell5.getObject()));
 //BA.debugLineNum = 254;BA.debugLine="sheet3.AddCell(cell6)";
_sheet3.AddCell((jxl.write.WritableCell)(_cell6.getObject()));
 }
};
 };
 //BA.debugLineNum = 257;BA.debugLine="newWorkbook.Write";
_newworkbook.Write();
 //BA.debugLineNum = 258;BA.debugLine="newWorkbook.Close";
_newworkbook.Close();
 //BA.debugLineNum = 259;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 260;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return "";
}
public static String  _btngalaxy_click() throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub btnGalaxy_Click";
 //BA.debugLineNum = 159;BA.debugLine="StartActivity(\"GalaxyMaint\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("GalaxyMaint"));
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _btnstationsmaint_click() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub btnStationsMaint_Click";
 //BA.debugLineNum = 167;BA.debugLine="StartActivity(\"StationsMaint\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("StationsMaint"));
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _btnstockmaint_click() throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub btnStockMaint_Click";
 //BA.debugLineNum = 171;BA.debugLine="StartActivity(\"StockMarketMaint\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("StockMarketMaint"));
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _btnsystemsmaint_click() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Sub btnSystemsMaint_Click";
 //BA.debugLineNum = 163;BA.debugLine="StartActivity(\"SystemsMaint\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("SystemsMaint"));
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _distancesave() throws Exception{
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 510;BA.debugLine="Sub DistanceSave";
 //BA.debugLineNum = 511;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 513;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 514;BA.debugLine="whereclause.Put(\"MaxLYID\", 1)";
_whereclause.Put((Object)("MaxLYID"),(Object)(1));
 //BA.debugLineNum = 515;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"MaxLYTrade";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"MaxLYTradeDistance","TradeDistance",(Object)(mostCurrent._starter._maxlydist),_whereclause);
 //BA.debugLineNum = 517;BA.debugLine="PopulateCurrLocation";
_populatecurrlocation();
 //BA.debugLineNum = 519;BA.debugLine="End Sub";
return "";
}
public static String  _edtmaxdist_enterpressed() throws Exception{
 //BA.debugLineNum = 481;BA.debugLine="Sub edtMaxDist_EnterPressed";
 //BA.debugLineNum = 482;BA.debugLine="Starter.MaxLYDist = edtMaxDist.Text";
mostCurrent._starter._maxlydist = (int)(Double.parseDouble(mostCurrent._edtmaxdist.getText()));
 //BA.debugLineNum = 483;BA.debugLine="DistanceSave";
_distancesave();
 //BA.debugLineNum = 485;BA.debugLine="End Sub";
return "";
}
public static String  _fillmarketlists() throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub FillMarketLists";
 //BA.debugLineNum = 385;BA.debugLine="Q = \"SELECT SM.ComGrpDesc AS [Commodity Group], S";
mostCurrent._q = "SELECT SM.ComGrpDesc AS [Commodity Group], SM.CommodDesc AS [Commodity], SL.StockLevelDesc AS [Stock Level] FROM StockMarket SM LEFT JOIN StockLevels SL ON SM.StockLevelRank = SL.StockLevelRank WHERE SM.SystemName = ? AND SM.StationName = ? AND SM.StockTypeDesc = 'Supply' ORDER BY SM.StockLevelRank DESC, SM.ComGrpDesc ASC, SM.CommodDesc ASC";
 //BA.debugLineNum = 386;BA.debugLine="wbvMarketSupply.LoadHtml(DBUtils.ExecuteHtml(Star";
mostCurrent._wbvmarketsupply.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{mostCurrent._starter._currlocation,mostCurrent._starter._currstation},(int) (0),anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 388;BA.debugLine="Q = \"SELECT SM.ComGrpDesc AS [Commodity Group], S";
mostCurrent._q = "SELECT SM.ComGrpDesc AS [Commodity Group], SM.CommodDesc AS [Commodity], SL.StockLevelDesc AS [Stock Level] FROM StockMarket SM LEFT JOIN StockLevels SL ON SM.StockLevelRank = SL.StockLevelRank WHERE SM.SystemName = ? AND SM.StationName = ? AND SM.StockTypeDesc = 'Demand' ORDER BY SM.StockLevelRank DESC, SM.ComGrpDesc ASC, SM.CommodDesc ASC";
 //BA.debugLineNum = 389;BA.debugLine="wbvMarketDemand.LoadHtml(DBUtils.ExecuteHtml(Star";
mostCurrent._wbvmarketdemand.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{mostCurrent._starter._currlocation,mostCurrent._starter._currstation},(int) (0),anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 391;BA.debugLine="MarketLists 'Sets the ID lookup list of the commo";
_marketlists();
 //BA.debugLineNum = 393;BA.debugLine="PopulateNonItems";
_populatenonitems();
 //BA.debugLineNum = 395;BA.debugLine="End Sub";
return "";
}
public static String  _fillraressystems(String _rarehome) throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curstest = null;
double _result = 0;
double _result2 = 0;
anywheresoftware.b4a.objects.collections.Map _record = null;
 //BA.debugLineNum = 608;BA.debugLine="Sub FillRaresSystems(RareHome As String)";
 //BA.debugLineNum = 609;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 610;BA.debugLine="Dim CursSystems As Cursor";
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 611;BA.debugLine="Dim CursTest As Cursor";
_curstest = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 612;BA.debugLine="Dim Result As Double";
_result = 0;
 //BA.debugLineNum = 613;BA.debugLine="Dim Result2 As Double";
_result2 = 0;
 //BA.debugLineNum = 614;BA.debugLine="Dim record As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 616;BA.debugLine="ProgressDialogShow(\"Please wait while we calculat";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Please wait while we calculate the rares...");
 //BA.debugLineNum = 619;BA.debugLine="If SQLUtils.TableExists(Starter.SQLExec,\"TempRare";
if (mostCurrent._sqlutils._tableexists(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"TempRareDist")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 620;BA.debugLine="Q = \"CREATE TABLE TempRareDist(TRDID INTEGER PRI";
mostCurrent._q = "CREATE TABLE TempRareDist(TRDID INTEGER PRIMARY KEY, SystemName TEXT, DistLY REAL, LY REAL)";
 //BA.debugLineNum = 621;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 }else {
 //BA.debugLineNum = 623;BA.debugLine="Q = \"DELETE FROM TempRareDist\"";
mostCurrent._q = "DELETE FROM TempRareDist";
 //BA.debugLineNum = 624;BA.debugLine="Starter.SQLExec.ExecNonQuery(Q)";
mostCurrent._starter._sqlexec.ExecNonQuery(mostCurrent._q);
 };
 //BA.debugLineNum = 628;BA.debugLine="Q = \"SELECT S.SystemName FROM Systems S WHERE S.L";
mostCurrent._q = "SELECT S.SystemName FROM Systems S WHERE S.LYfromCurrent <= "+BA.NumberToString(mostCurrent._starter._maxlydist)+" ORDER BY S.SystemName ASC";
 //BA.debugLineNum = 629;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery(Q)";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 630;BA.debugLine="If CursSystems.RowCount > 0 Then";
if (_curssystems.getRowCount()>0) { 
 //BA.debugLineNum = 631;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 632;BA.debugLine="For i = 0 To CursSystems.RowCount - 1";
{
final int step19 = 1;
final int limit19 = (int) (_curssystems.getRowCount()-1);
for (_i = (int) (0) ; (step19 > 0 && _i <= limit19) || (step19 < 0 && _i >= limit19); _i = ((int)(0 + _i + step19)) ) {
 //BA.debugLineNum = 633;BA.debugLine="CursSystems.Position = i";
_curssystems.setPosition(_i);
 //BA.debugLineNum = 634;BA.debugLine="Result = Elite.DistanceBetween(CursSystems.GetS";
_result = mostCurrent._elite._distancebetween(mostCurrent.activityBA,_curssystems.GetString("SystemName"),_rarehome);
 //BA.debugLineNum = 635;BA.debugLine="Result2 = Elite.DistanceBetween(CursSystems.Get";
_result2 = mostCurrent._elite._distancebetween(mostCurrent.activityBA,_curssystems.GetString("SystemName"),mostCurrent._starter._currlocation);
 //BA.debugLineNum = 636;BA.debugLine="record.Put(\"TRDID\", Null)";
_record.Put((Object)("TRDID"),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 637;BA.debugLine="record.Put(\"SystemName\", CursSystems.GetString(";
_record.Put((Object)("SystemName"),(Object)(_curssystems.GetString("SystemName")));
 //BA.debugLineNum = 638;BA.debugLine="record.Put(\"DistLY\", Result)";
_record.Put((Object)("DistLY"),(Object)(_result));
 //BA.debugLineNum = 639;BA.debugLine="record.Put(\"LY\", Result2)";
_record.Put((Object)("LY"),(Object)(_result2));
 //BA.debugLineNum = 640;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"TempR";
mostCurrent._sqlutils._table_insertmap(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"TempRareDist",_record);
 }
};
 };
 //BA.debugLineNum = 643;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 645;BA.debugLine="Q = \"SELECT TRD.SystemName As [Star System], TRD.";
mostCurrent._q = "SELECT TRD.SystemName As [Star System], TRD.DistLY As [Dist from Rare (LY)], TRD.LY As [Dist from Current (LY)] FROM TempRareDist TRD WHERE TRD.DistLY >= 100 ORDER BY TRD.LY ASC";
 //BA.debugLineNum = 647;BA.debugLine="CursTest = Starter.SQLExec.ExecQuery(Q)";
_curstest.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 648;BA.debugLine="If CursTest.RowCount > 0 Then";
if (_curstest.getRowCount()>0) { 
 //BA.debugLineNum = 649;BA.debugLine="wbvTradeSystemsRares.LoadHtml(DBUtils.ExecuteHtm";
mostCurrent._wbvtradesystemsrares.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0),anywheresoftware.b4a.keywords.Common.False));
 }else {
 //BA.debugLineNum = 651;BA.debugLine="lblTradeRares.Text = \"Location of Rare not known";
mostCurrent._lbltraderares.setText((Object)("Location of Rare not known unable to plot"));
 };
 //BA.debugLineNum = 653;BA.debugLine="CursTest.Close";
_curstest.Close();
 //BA.debugLineNum = 655;BA.debugLine="DBUtils.DropTable(Starter.SQLExec,\"TempRareDist\")";
mostCurrent._dbutils._droptable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"TempRareDist");
 //BA.debugLineNum = 657;BA.debugLine="ProgressDialogHide()";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 659;BA.debugLine="End Sub";
return "";
}
public static String  _filltradesystems(String _supdem,String _commoddesc,int _lvl,String _filltype) throws Exception{
String _searchtype = "";
int _searchlevel = 0;
 //BA.debugLineNum = 709;BA.debugLine="Sub FillTradeSystems(SupDem As String, CommodDesc";
 //BA.debugLineNum = 710;BA.debugLine="Dim SearchType As String";
_searchtype = "";
 //BA.debugLineNum = 711;BA.debugLine="Dim SearchLevel As Int";
_searchlevel = 0;
 //BA.debugLineNum = 715;BA.debugLine="SearchType = \"Supply\"";
_searchtype = "Supply";
 //BA.debugLineNum = 716;BA.debugLine="If SupDem = \"Supply\" Then";
if ((_supdem).equals("Supply")) { 
 //BA.debugLineNum = 717;BA.debugLine="SearchType = \"Demand\"";
_searchtype = "Demand";
 };
 //BA.debugLineNum = 720;BA.debugLine="If Lvl = 4 Then";
if (_lvl==4) { 
 //BA.debugLineNum = 721;BA.debugLine="SearchLevel = 1";
_searchlevel = (int) (1);
 }else if(_lvl==2) { 
 //BA.debugLineNum = 723;BA.debugLine="SearchLevel = 2";
_searchlevel = (int) (2);
 }else if(_lvl==1) { 
 //BA.debugLineNum = 725;BA.debugLine="SearchLevel = 4";
_searchlevel = (int) (4);
 };
 //BA.debugLineNum = 728;BA.debugLine="Q = \"SELECT SM.SystemName As [Star System], SM.St";
mostCurrent._q = "SELECT SM.SystemName As [Star System], SM.StationName As [Station], S.LYfromCurrent AS [Dist (LY)], SL.StockLevelDesc AS [Stock Level] FROM StockMarket SM LEFT JOIN Systems S ON SM.SystemName = S.SystemName LEFT JOIN StockLevels SL ON SM.StockLevelRank = SL.StockLevelRank WHERE S.ExactLocation = 1 AND S.LYfromCurrent <= "+BA.NumberToString(mostCurrent._starter._maxlydist)+" AND SM.CommodDesc = ? AND SM.StockTypeDesc = ? AND SM.StockLevelRank >= "+BA.NumberToString(_searchlevel)+" ORDER BY SM.StockLevelRank DESC, S.LYfromCurrent ASC";
 //BA.debugLineNum = 729;BA.debugLine="If FillType = \"TRADE\" Then";
if ((_filltype).equals("TRADE")) { 
 //BA.debugLineNum = 730;BA.debugLine="If SupDem = \"Supply\" Then";
if ((_supdem).equals("Supply")) { 
 //BA.debugLineNum = 731;BA.debugLine="wbvTradeSystemsSup.LoadHtml(DBUtils.ExecuteHtml";
mostCurrent._wbvtradesystemssup.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{_commoddesc,_searchtype},(int) (0),anywheresoftware.b4a.keywords.Common.False));
 }else {
 //BA.debugLineNum = 733;BA.debugLine="wbvTradeSystemsDem.LoadHtml(DBUtils.ExecuteHtml";
mostCurrent._wbvtradesystemsdem.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{_commoddesc,_searchtype},(int) (0),anywheresoftware.b4a.keywords.Common.False));
 };
 };
 //BA.debugLineNum = 736;BA.debugLine="If FillType = \"NONTRADE\" Then";
if ((_filltype).equals("NONTRADE")) { 
 //BA.debugLineNum = 737;BA.debugLine="wbvTradeSystemsNon.LoadHtml(DBUtils.ExecuteHtml";
mostCurrent._wbvtradesystemsnon.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{_commoddesc,_searchtype},(int) (0),anywheresoftware.b4a.keywords.Common.False));
 };
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 39;BA.debugLine="Private btnDBAdmin As Button";
mostCurrent._btndbadmin = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btnSystemsMaint As Button";
mostCurrent._btnsystemsmaint = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnStationsMaint As Button";
mostCurrent._btnstationsmaint = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnStockMaint As Button";
mostCurrent._btnstockmaint = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnExport As Button";
mostCurrent._btnexport = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private btnGalaxy As Button";
mostCurrent._btngalaxy = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private edtMaxDist As EditText";
mostCurrent._edtmaxdist = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private wbvMarketSupply As WebView";
mostCurrent._wbvmarketsupply = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private wbvMarketDemand As WebView";
mostCurrent._wbvmarketdemand = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private wbvKnownRares As WebView";
mostCurrent._wbvknownrares = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private wbvTradeSystemsSup As WebView";
mostCurrent._wbvtradesystemssup = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private wbvTradeSystemsDem As WebView";
mostCurrent._wbvtradesystemsdem = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private wbvTradeSystemsNon As WebView";
mostCurrent._wbvtradesystemsnon = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private wbvTradeSystemsRares As WebView";
mostCurrent._wbvtradesystemsrares = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private spnSystem As Spinner";
mostCurrent._spnsystem = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private spnStation As Spinner";
mostCurrent._spnstation = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private spnNonItems As Spinner";
mostCurrent._spnnonitems = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private spnFindSystem As Spinner";
mostCurrent._spnfindsystem = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblCurrentSystem As Label";
mostCurrent._lblcurrentsystem = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblCurrentStation As Label";
mostCurrent._lblcurrentstation = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private lblSysEcon As Label";
mostCurrent._lblsysecon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private lblStatEcon As Label";
mostCurrent._lblstatecon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private lblMaxLY As Label";
mostCurrent._lblmaxly = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private lblDBSize As Label";
mostCurrent._lbldbsize = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private lblSize As Label";
mostCurrent._lblsize = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private lblAnchorNum As Label";
mostCurrent._lblanchornum = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private lblTradeSup As Label";
mostCurrent._lbltradesup = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private lblTradeDem As Label";
mostCurrent._lbltradedem = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private lblTradeNon As Label";
mostCurrent._lbltradenon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private lblTradeRares As Label";
mostCurrent._lbltraderares = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private lblDestination As Label";
mostCurrent._lbldestination = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private thMain As TabHost";
mostCurrent._thmain = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private TabManager As TabHostExtras";
mostCurrent._tabmanager = new uk.co.martinpearman.b4a.tabhostextras.TabHostExtras();
 //BA.debugLineNum = 79;BA.debugLine="Private Q As String";
mostCurrent._q = "";
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _loadcurrlocation() throws Exception{
String _locname = "";
 //BA.debugLineNum = 265;BA.debugLine="Sub LoadCurrLocation";
 //BA.debugLineNum = 266;BA.debugLine="Dim LocName As String";
_locname = "";
 //BA.debugLineNum = 269;BA.debugLine="Q = \"SELECT SystemName FROM Location WHERE Locati";
mostCurrent._q = "SELECT SystemName FROM Location WHERE LocationID = 1";
 //BA.debugLineNum = 270;BA.debugLine="LocName = Starter.SQLExec.ExecQuerySingleResult(Q";
_locname = mostCurrent._starter._sqlexec.ExecQuerySingleResult(mostCurrent._q);
 //BA.debugLineNum = 271;BA.debugLine="spnSystem.SelectedIndex = spnSystem.IndexOf(LocNa";
mostCurrent._spnsystem.setSelectedIndex(mostCurrent._spnsystem.IndexOf(_locname));
 //BA.debugLineNum = 272;BA.debugLine="Starter.CurrLocation = LocName";
mostCurrent._starter._currlocation = _locname;
 //BA.debugLineNum = 274;BA.debugLine="SysEconomy";
_syseconomy();
 //BA.debugLineNum = 276;BA.debugLine="End Sub";
return "";
}
public static String  _loadcurrmaxly() throws Exception{
int _maxdist = 0;
 //BA.debugLineNum = 293;BA.debugLine="Sub LoadCurrMaxLY";
 //BA.debugLineNum = 294;BA.debugLine="Dim MaxDist As Int";
_maxdist = 0;
 //BA.debugLineNum = 297;BA.debugLine="Q = \"SELECT TradeDistance FROM MaxLYTradeDistance";
mostCurrent._q = "SELECT TradeDistance FROM MaxLYTradeDistance WHERE MaxLYID = 1";
 //BA.debugLineNum = 298;BA.debugLine="MaxDist = Starter.SQLExec.ExecQuerySingleResult(Q";
_maxdist = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult(mostCurrent._q)));
 //BA.debugLineNum = 299;BA.debugLine="edtMaxDist.Text = MaxDist";
mostCurrent._edtmaxdist.setText((Object)(_maxdist));
 //BA.debugLineNum = 300;BA.debugLine="Starter.MaxLYDist = MaxDist";
mostCurrent._starter._maxlydist = _maxdist;
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _loadcurrstation() throws Exception{
String _statname = "";
 //BA.debugLineNum = 278;BA.debugLine="Sub LoadCurrStation";
 //BA.debugLineNum = 279;BA.debugLine="Dim StatName As String";
_statname = "";
 //BA.debugLineNum = 282;BA.debugLine="Q = \"SELECT StationName FROM Location WHERE Locat";
mostCurrent._q = "SELECT StationName FROM Location WHERE LocationID = 1";
 //BA.debugLineNum = 283;BA.debugLine="StatName = Starter.SQLExec.ExecQuerySingleResult(";
_statname = mostCurrent._starter._sqlexec.ExecQuerySingleResult(mostCurrent._q);
 //BA.debugLineNum = 284;BA.debugLine="spnStation.SelectedIndex = spnStation.IndexOf(Sta";
mostCurrent._spnstation.setSelectedIndex(mostCurrent._spnstation.IndexOf(_statname));
 //BA.debugLineNum = 285;BA.debugLine="Starter.CurrStation = StatName";
mostCurrent._starter._currstation = _statname;
 //BA.debugLineNum = 287;BA.debugLine="StatEconomy";
_stateconomy();
 //BA.debugLineNum = 289;BA.debugLine="FillMarketLists";
_fillmarketlists();
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _locationsave() throws Exception{
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 489;BA.debugLine="Sub LocationSave";
 //BA.debugLineNum = 490;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 492;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 493;BA.debugLine="whereclause.Put(\"LocationID\", 1)";
_whereclause.Put((Object)("LocationID"),(Object)(1));
 //BA.debugLineNum = 494;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"Location\",";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Location","SystemName",(Object)(mostCurrent._starter._currlocation),_whereclause);
 //BA.debugLineNum = 495;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"Location\",";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Location","StationName",(Object)(""),_whereclause);
 //BA.debugLineNum = 497;BA.debugLine="Elite.UpdateSysLY";
mostCurrent._elite._updatesysly(mostCurrent.activityBA);
 //BA.debugLineNum = 499;BA.debugLine="End Sub";
return "";
}
public static String  _marketlists() throws Exception{
 //BA.debugLineNum = 373;BA.debugLine="Sub MarketLists";
 //BA.debugLineNum = 374;BA.debugLine="Q = \"SELECT * FROM StockMarket SM WHERE SM.System";
mostCurrent._q = "SELECT * FROM StockMarket SM WHERE SM.SystemName = ? AND SM.StationName = ? AND SM.StockTypeDesc = 'Supply' ORDER BY SM.StockLevelRank DESC, SM.ComGrpDesc ASC, SM.CommodDesc ASC";
 //BA.debugLineNum = 375;BA.debugLine="supplyMarketList = DBUtils.ExecuteMemoryTable(St";
_supplymarketlist = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{mostCurrent._starter._currlocation,mostCurrent._starter._currstation},(int) (0));
 //BA.debugLineNum = 377;BA.debugLine="Q = \"SELECT * FROM StockMarket SM WHERE SM.System";
mostCurrent._q = "SELECT * FROM StockMarket SM WHERE SM.SystemName = ? AND SM.StationName = ? AND SM.StockTypeDesc = 'Demand' ORDER BY SM.StockLevelRank DESC, SM.ComGrpDesc ASC, SM.CommodDesc ASC";
 //BA.debugLineNum = 378;BA.debugLine="demandMarketList = DBUtils.ExecuteMemoryTable(St";
_demandmarketlist = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,new String[]{mostCurrent._starter._currlocation,mostCurrent._starter._currstation},(int) (0));
 //BA.debugLineNum = 380;BA.debugLine="RaresPopulate";
_rarespopulate();
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return "";
}
public static String  _populateallegtypes() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursalleg = null;
 //BA.debugLineNum = 669;BA.debugLine="Sub PopulateAllegTypes";
 //BA.debugLineNum = 670;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 671;BA.debugLine="Dim CursAlleg As Cursor";
_cursalleg = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 674;BA.debugLine="Q = \"SELECT AllegDesc FROM Allegiance ORDER BY Nu";
mostCurrent._q = "SELECT AllegDesc FROM Allegiance ORDER BY NumOfEntries DESC";
 //BA.debugLineNum = 675;BA.debugLine="CursAlleg = Starter.SQLExec.ExecQuery(Q)";
_cursalleg.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 676;BA.debugLine="spnFindSystem.Add(\"Not Set\")";
mostCurrent._spnfindsystem.Add("Not Set");
 //BA.debugLineNum = 677;BA.debugLine="If CursAlleg.RowCount > 0 Then";
if (_cursalleg.getRowCount()>0) { 
 //BA.debugLineNum = 678;BA.debugLine="spnFindSystem.Clear";
mostCurrent._spnfindsystem.Clear();
 //BA.debugLineNum = 679;BA.debugLine="spnFindSystem.Add(\" \")";
mostCurrent._spnfindsystem.Add(" ");
 //BA.debugLineNum = 680;BA.debugLine="For i = 0 To CursAlleg.RowCount - 1";
{
final int step9 = 1;
final int limit9 = (int) (_cursalleg.getRowCount()-1);
for (_i = (int) (0) ; (step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9); _i = ((int)(0 + _i + step9)) ) {
 //BA.debugLineNum = 681;BA.debugLine="CursAlleg.Position = i";
_cursalleg.setPosition(_i);
 //BA.debugLineNum = 682;BA.debugLine="spnFindSystem.Add(CursAlleg.GetString(\"AllegDes";
mostCurrent._spnfindsystem.Add(_cursalleg.GetString("AllegDesc"));
 }
};
 };
 //BA.debugLineNum = 685;BA.debugLine="CursAlleg.Close";
_cursalleg.Close();
 //BA.debugLineNum = 687;BA.debugLine="End Sub";
return "";
}
public static String  _populatecurrlocation() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
 //BA.debugLineNum = 304;BA.debugLine="Sub PopulateCurrLocation";
 //BA.debugLineNum = 305;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 306;BA.debugLine="Dim CursSystems As Cursor";
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 309;BA.debugLine="Q = \"SELECT SystemName FROM Systems WHERE LYfromC";
mostCurrent._q = "SELECT SystemName FROM Systems WHERE LYfromCurrent <= "+BA.NumberToString(mostCurrent._starter._maxlydist)+" ORDER BY LYfromCurrent ASC, SystemName ASC";
 //BA.debugLineNum = 310;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery(Q)";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 311;BA.debugLine="spnSystem.Add(\"Not Set\")";
mostCurrent._spnsystem.Add("Not Set");
 //BA.debugLineNum = 312;BA.debugLine="If CursSystems.RowCount > 0 Then";
if (_curssystems.getRowCount()>0) { 
 //BA.debugLineNum = 313;BA.debugLine="spnSystem.Clear";
mostCurrent._spnsystem.Clear();
 //BA.debugLineNum = 314;BA.debugLine="For i = 0 To CursSystems.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (_curssystems.getRowCount()-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 315;BA.debugLine="CursSystems.Position = i";
_curssystems.setPosition(_i);
 //BA.debugLineNum = 316;BA.debugLine="spnSystem.Add(CursSystems.GetString(\"SystemName";
mostCurrent._spnsystem.Add(_curssystems.GetString("SystemName"));
 }
};
 };
 //BA.debugLineNum = 319;BA.debugLine="spnSystem.SelectedIndex = spnSystem.IndexOf(Start";
mostCurrent._spnsystem.setSelectedIndex(mostCurrent._spnsystem.IndexOf(mostCurrent._starter._currlocation));
 //BA.debugLineNum = 320;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 322;BA.debugLine="PopulateCurrStation";
_populatecurrstation();
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _populatecurrstation() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstations = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstockcount = null;
 //BA.debugLineNum = 326;BA.debugLine="Sub PopulateCurrStation";
 //BA.debugLineNum = 327;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 328;BA.debugLine="Dim CursStations,CursStockCount As Cursor";
_cursstations = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursstockcount = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 331;BA.debugLine="Q = \"SELECT StationName FROM Stations WHERE Syste";
mostCurrent._q = "SELECT StationName FROM Stations WHERE SystemName = ? ORDER BY StationName ASC";
 //BA.debugLineNum = 332;BA.debugLine="CursStations = Starter.SQLExec.ExecQuery2(Q,Array";
_cursstations.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 333;BA.debugLine="spnStation.Add(\"Not Set\")";
mostCurrent._spnstation.Add("Not Set");
 //BA.debugLineNum = 334;BA.debugLine="If CursStations.RowCount > 0 Then";
if (_cursstations.getRowCount()>0) { 
 //BA.debugLineNum = 335;BA.debugLine="spnStation.Clear";
mostCurrent._spnstation.Clear();
 //BA.debugLineNum = 336;BA.debugLine="spnStation.Add(\"Not Set\")";
mostCurrent._spnstation.Add("Not Set");
 //BA.debugLineNum = 337;BA.debugLine="Q = \"SELECT * FROM StockMarket WHERE SystemName";
mostCurrent._q = "SELECT * FROM StockMarket WHERE SystemName = ? AND StationName = ? ORDER BY SystemName ASC, StationName Asc";
 //BA.debugLineNum = 338;BA.debugLine="For i = 0 To CursStations.RowCount - 1";
{
final int step10 = 1;
final int limit10 = (int) (_cursstations.getRowCount()-1);
for (_i = (int) (0) ; (step10 > 0 && _i <= limit10) || (step10 < 0 && _i >= limit10); _i = ((int)(0 + _i + step10)) ) {
 //BA.debugLineNum = 339;BA.debugLine="CursStations.Position = i";
_cursstations.setPosition(_i);
 //BA.debugLineNum = 340;BA.debugLine="CursStockCount = Starter.SQLExec.ExecQuery2(Q,A";
_cursstockcount.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation,_cursstations.GetString("StationName")})));
 //BA.debugLineNum = 341;BA.debugLine="If CursStockCount.RowCount > 0 Then";
if (_cursstockcount.getRowCount()>0) { 
 //BA.debugLineNum = 342;BA.debugLine="spnStation.Add(CursStations.GetString(\"Station";
mostCurrent._spnstation.Add(_cursstations.GetString("StationName"));
 };
 //BA.debugLineNum = 344;BA.debugLine="CursStockCount.Close";
_cursstockcount.Close();
 }
};
 };
 //BA.debugLineNum = 347;BA.debugLine="spnStation.SelectedIndex = spnStation.IndexOf(Sta";
mostCurrent._spnstation.setSelectedIndex(mostCurrent._spnstation.IndexOf(mostCurrent._starter._currstation));
 //BA.debugLineNum = 348;BA.debugLine="CursStations.Close";
_cursstations.Close();
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _populatenonitems() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursitems = null;
 //BA.debugLineNum = 352;BA.debugLine="Sub PopulateNonItems";
 //BA.debugLineNum = 353;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 354;BA.debugLine="Dim CursItems As Cursor";
_cursitems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 357;BA.debugLine="Q = \"SELECT SM.CommodDesc As [Commodity] FROM Sto";
mostCurrent._q = "SELECT SM.CommodDesc As [Commodity] FROM StockMarket SM WHERE SM.SystemName = ? AND SM.StationName = ? AND SM.StockTypeDesc = 'Not Tradeable' ORDER BY SM.ComGrpDesc ASC, SM.CommodDesc ASC";
 //BA.debugLineNum = 358;BA.debugLine="CursItems = Starter.SQLExec.ExecQuery2(Q,Array As";
_cursitems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation,mostCurrent._starter._currstation})));
 //BA.debugLineNum = 359;BA.debugLine="spnNonItems.Add(\" \")";
mostCurrent._spnnonitems.Add(" ");
 //BA.debugLineNum = 360;BA.debugLine="If CursItems.RowCount > 0 Then";
if (_cursitems.getRowCount()>0) { 
 //BA.debugLineNum = 361;BA.debugLine="spnNonItems.Clear";
mostCurrent._spnnonitems.Clear();
 //BA.debugLineNum = 362;BA.debugLine="spnNonItems.Add(\" \")";
mostCurrent._spnnonitems.Add(" ");
 //BA.debugLineNum = 363;BA.debugLine="For i = 0 To CursItems.RowCount - 1";
{
final int step9 = 1;
final int limit9 = (int) (_cursitems.getRowCount()-1);
for (_i = (int) (0) ; (step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9); _i = ((int)(0 + _i + step9)) ) {
 //BA.debugLineNum = 364;BA.debugLine="CursItems.Position = i";
_cursitems.setPosition(_i);
 //BA.debugLineNum = 365;BA.debugLine="spnNonItems.Add(CursItems.GetString(\"Commodity\"";
mostCurrent._spnnonitems.Add(_cursitems.GetString("Commodity"));
 }
};
 };
 //BA.debugLineNum = 368;BA.debugLine="spnNonItems.SelectedIndex = 0";
mostCurrent._spnnonitems.setSelectedIndex((int) (0));
 //BA.debugLineNum = 369;BA.debugLine="CursItems.Close";
_cursitems.Close();
 //BA.debugLineNum = 371;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
dbadmin._process_globals();
galaxymaint._process_globals();
systemsmaint._process_globals();
stationsmaint._process_globals();
stockmarketmaint._process_globals();
splash._process_globals();
functions._process_globals();
dbutils._process_globals();
sqlutils._process_globals();
elite._process_globals();
commodupdate._process_globals();
edtables._process_globals();
anchordefine._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 28;BA.debugLine="Dim supplyMarketList As List";
_supplymarketlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 29;BA.debugLine="Dim demandMarketList As List";
_demandmarketlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 30;BA.debugLine="Dim raresMarketList As List";
_raresmarketlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 31;BA.debugLine="Dim DBFileSize As Long";
_dbfilesize = 0L;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _rarespopulate() throws Exception{
 //BA.debugLineNum = 397;BA.debugLine="Sub RaresPopulate";
 //BA.debugLineNum = 398;BA.debugLine="Q = \"SELECT RR.ComGrpDesc AS [Commodity Group], R";
mostCurrent._q = "SELECT RR.ComGrpDesc AS [Commodity Group], RR.RareDesc AS [Commodity], RR.RareSystem AS [Star System], RR.RareStation AS [Station], S.LYfromCurrent AS [Distance (LY)] FROM RareCommodities RR LEFT JOIN Systems S ON TRIM(RR.RareSystem) = TRIM(S.SystemName) ORDER BY S.LYfromCurrent ASC, RR.ComGrpDesc ASC";
 //BA.debugLineNum = 399;BA.debugLine="wbvKnownRares.LoadHtml(DBUtils.ExecuteHtml(Starte";
mostCurrent._wbvknownrares.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0),anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 401;BA.debugLine="Q = \"SELECT RR.ComGrpDesc, RR.RareDesc, RR.RareSy";
mostCurrent._q = "SELECT RR.ComGrpDesc, RR.RareDesc, RR.RareSystem, RR.RareStation, S.LYfromCurrent FROM RareCommodities RR LEFT JOIN Systems S ON TRIM(RR.RareSystem) = TRIM(S.SystemName) ORDER BY S.LYfromCurrent ASC, RR.ComGrpDesc ASC";
 //BA.debugLineNum = 402;BA.debugLine="raresMarketList = DBUtils.ExecuteMemoryTable(Star";
_raresmarketlist = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _spnfindsystem_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursfindsys = null;
 //BA.debugLineNum = 689;BA.debugLine="Sub spnFindSystem_ItemClick (Position As Int, Valu";
 //BA.debugLineNum = 690;BA.debugLine="Dim CursFindSys As Cursor";
_cursfindsys = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 692;BA.debugLine="Q = \"SELECT SystemName, LYfromCurrent, AllegDesc";
mostCurrent._q = "SELECT SystemName, LYfromCurrent, AllegDesc FROM Systems WHERE AllegDesc = '"+BA.ObjectToString(_value)+"' ORDER BY LYfromCurrent ASC";
 //BA.debugLineNum = 693;BA.debugLine="CursFindSys = Starter.SQLExec.ExecQuery(Q)";
_cursfindsys.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 694;BA.debugLine="If CursFindSys.RowCount > 0 Then";
if (_cursfindsys.getRowCount()>0) { 
 //BA.debugLineNum = 695;BA.debugLine="CursFindSys.Position = 0";
_cursfindsys.setPosition((int) (0));
 //BA.debugLineNum = 696;BA.debugLine="lblDestination.Text = CursFindSys.GetString(\"Sys";
mostCurrent._lbldestination.setText((Object)(_cursfindsys.GetString("SystemName")+" - "+_cursfindsys.GetString("LYfromCurrent")+" Light Years away"));
 }else {
 //BA.debugLineNum = 698;BA.debugLine="lblDestination.Text = \"<< No Systems Found >>\"";
mostCurrent._lbldestination.setText((Object)("<< No Systems Found >>"));
 };
 //BA.debugLineNum = 700;BA.debugLine="CursFindSys.Close";
_cursfindsys.Close();
 //BA.debugLineNum = 702;BA.debugLine="End Sub";
return "";
}
public static String  _spnnonitems_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 578;BA.debugLine="Sub spnNonItems_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 579;BA.debugLine="FillTradeSystems(\"Demand\",Value,4,\"NONTRADE\")";
_filltradesystems("Demand",BA.ObjectToString(_value),(int) (4),"NONTRADE");
 //BA.debugLineNum = 581;BA.debugLine="lblTradeNon.Text = \"Places that SELL - \" & Value";
mostCurrent._lbltradenon.setText((Object)("Places that SELL - "+BA.ObjectToString(_value)));
 //BA.debugLineNum = 582;BA.debugLine="End Sub";
return "";
}
public static String  _spnstation_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 471;BA.debugLine="Sub spnStation_ItemClick (Position As Int, Value A";
 //BA.debugLineNum = 472;BA.debugLine="Starter.CurrStation = Value";
mostCurrent._starter._currstation = BA.ObjectToString(_value);
 //BA.debugLineNum = 473;BA.debugLine="StationSave";
_stationsave();
 //BA.debugLineNum = 475;BA.debugLine="StatEconomy";
_stateconomy();
 //BA.debugLineNum = 477;BA.debugLine="FillMarketLists";
_fillmarketlists();
 //BA.debugLineNum = 478;BA.debugLine="End Sub";
return "";
}
public static String  _spnsystem_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 437;BA.debugLine="Sub spnSystem_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 438;BA.debugLine="Starter.CurrLocation = Value";
mostCurrent._starter._currlocation = BA.ObjectToString(_value);
 //BA.debugLineNum = 439;BA.debugLine="LocationSave";
_locationsave();
 //BA.debugLineNum = 441;BA.debugLine="SysEconomy";
_syseconomy();
 //BA.debugLineNum = 443;BA.debugLine="Starter.CurrStation = \"Not Set\"";
mostCurrent._starter._currstation = "Not Set";
 //BA.debugLineNum = 444;BA.debugLine="lblStatEcon.Text = \"\"";
mostCurrent._lblstatecon.setText((Object)(""));
 //BA.debugLineNum = 445;BA.debugLine="spnStation.SelectedIndex = 0";
mostCurrent._spnstation.setSelectedIndex((int) (0));
 //BA.debugLineNum = 447;BA.debugLine="wbvMarketSupply.LoadHtml(\"<html><body>Select Stat";
mostCurrent._wbvmarketsupply.LoadHtml("<html><body>Select Station to view data</body></html>");
 //BA.debugLineNum = 448;BA.debugLine="wbvMarketDemand.LoadHtml(\"<html><body>Select Stat";
mostCurrent._wbvmarketdemand.LoadHtml("<html><body>Select Station to view data</body></html>");
 //BA.debugLineNum = 449;BA.debugLine="wbvTradeSystemsSup.LoadHtml(\"<html><body>Select C";
mostCurrent._wbvtradesystemssup.LoadHtml("<html><body>Select Commodity to view data</body></html>");
 //BA.debugLineNum = 450;BA.debugLine="wbvTradeSystemsDem.LoadHtml(\"<html><body>Select C";
mostCurrent._wbvtradesystemsdem.LoadHtml("<html><body>Select Commodity to view data</body></html>");
 //BA.debugLineNum = 451;BA.debugLine="wbvTradeSystemsNon.LoadHtml(\"<html><body>Select C";
mostCurrent._wbvtradesystemsnon.LoadHtml("<html><body>Select Commodity to view data</body></html>");
 //BA.debugLineNum = 453;BA.debugLine="PopulateCurrStation";
_populatecurrstation();
 //BA.debugLineNum = 455;BA.debugLine="spnFindSystem_ItemClick(spnFindSystem.SelectedInd";
_spnfindsystem_itemclick(mostCurrent._spnfindsystem.getSelectedIndex(),(Object)(mostCurrent._spnfindsystem.getSelectedItem()));
 //BA.debugLineNum = 456;BA.debugLine="spnNonItems.SelectedIndex = 0";
mostCurrent._spnnonitems.setSelectedIndex((int) (0));
 //BA.debugLineNum = 458;BA.debugLine="PopulateCurrLocation";
_populatecurrlocation();
 //BA.debugLineNum = 460;BA.debugLine="Starter.SystemMoves = Starter.SystemMoves + 1";
mostCurrent._starter._systemmoves = (int) (mostCurrent._starter._systemmoves+1);
 //BA.debugLineNum = 461;BA.debugLine="If Starter.SystemMoves >= 5 Then";
if (mostCurrent._starter._systemmoves>=5) { 
 //BA.debugLineNum = 462;BA.debugLine="Starter.SystemMoves = 0";
mostCurrent._starter._systemmoves = (int) (0);
 //BA.debugLineNum = 463;BA.debugLine="ProgressDialogShow(\"Please wait anchor locations";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Please wait anchor locations are being updated...");
 //BA.debugLineNum = 464;BA.debugLine="StartService(AnchorDefine)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._anchordefine.getObject()));
 //BA.debugLineNum = 465;BA.debugLine="lblAnchorNum.Text = Starter.AnchorsNumber";
mostCurrent._lblanchornum.setText((Object)(mostCurrent._starter._anchorsnumber));
 //BA.debugLineNum = 466;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 //BA.debugLineNum = 469;BA.debugLine="End Sub";
return "";
}
public static String  _stateconomy() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursstattype = null;
 //BA.debugLineNum = 419;BA.debugLine="Sub StatEconomy";
 //BA.debugLineNum = 420;BA.debugLine="Dim CursStatType As Cursor";
_cursstattype = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 422;BA.debugLine="Q = \"SELECT EconomyNum FROM Stations WHERE Statio";
mostCurrent._q = "SELECT EconomyNum FROM Stations WHERE StationName = ?";
 //BA.debugLineNum = 423;BA.debugLine="CursStatType = Starter.SQLExec.ExecQuery2(Q, Arra";
_cursstattype.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currstation})));
 //BA.debugLineNum = 424;BA.debugLine="If CursStatType.RowCount > 0 Then";
if (_cursstattype.getRowCount()>0) { 
 //BA.debugLineNum = 425;BA.debugLine="CursStatType.Position = 0";
_cursstattype.setPosition((int) (0));
 //BA.debugLineNum = 426;BA.debugLine="lblStatEcon.Text = Elite.FindEconomyNames(CursSt";
mostCurrent._lblstatecon.setText((Object)(mostCurrent._elite._findeconomynames(mostCurrent.activityBA,_cursstattype.GetInt("EconomyNum"))));
 };
 //BA.debugLineNum = 428;BA.debugLine="CursStatType.Close";
_cursstattype.Close();
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _stationsave() throws Exception{
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 501;BA.debugLine="Sub StationSave";
 //BA.debugLineNum = 502;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 504;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 505;BA.debugLine="whereclause.Put(\"LocationID\",1)";
_whereclause.Put((Object)("LocationID"),(Object)(1));
 //BA.debugLineNum = 506;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"Location\",";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Location","StationName",(Object)(mostCurrent._starter._currstation),_whereclause);
 //BA.debugLineNum = 508;BA.debugLine="End Sub";
return "";
}
public static String  _syseconomy() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystype = null;
 //BA.debugLineNum = 406;BA.debugLine="Sub SysEconomy";
 //BA.debugLineNum = 407;BA.debugLine="Dim CursSysType As Cursor";
_curssystype = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 409;BA.debugLine="Q = \"SELECT EconomyID FROM Systems WHERE SystemNa";
mostCurrent._q = "SELECT EconomyID FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 410;BA.debugLine="CursSysType = Starter.SQLExec.ExecQuery2(Q, Array";
_curssystype.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 411;BA.debugLine="If CursSysType.RowCount > 0 Then";
if (_curssystype.getRowCount()>0) { 
 //BA.debugLineNum = 412;BA.debugLine="CursSysType.Position = 0";
_curssystype.setPosition((int) (0));
 //BA.debugLineNum = 413;BA.debugLine="lblSysEcon.Text = Elite.FindEconomy(CursSysType.";
mostCurrent._lblsysecon.setText((Object)(mostCurrent._elite._findeconomy(mostCurrent.activityBA,_curssystype.GetInt("EconomyID"))));
 };
 //BA.debugLineNum = 415;BA.debugLine="CursSysType.Close";
_curssystype.Close();
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _thmain_tabchanged() throws Exception{
 //BA.debugLineNum = 432;BA.debugLine="Sub thMain_TabChanged";
 //BA.debugLineNum = 433;BA.debugLine="Functions.SetColours(Activity)";
mostCurrent._functions._setcolours(mostCurrent.activityBA,mostCurrent._activity);
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static boolean  _wbvknownrares_overrideurl(String _url) throws Exception{
String[] _values = null;
int _row = 0;
String[] _val = null;
 //BA.debugLineNum = 588;BA.debugLine="Sub wbvKnownRares_OverrideUrl (Url As String) As B";
 //BA.debugLineNum = 590;BA.debugLine="Dim Values() As String";
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 591;BA.debugLine="Dim Row As Int";
_row = 0;
 //BA.debugLineNum = 593;BA.debugLine="Values = Regex.Split(\"[.]\", Url.SubString(7))";
_values = anywheresoftware.b4a.keywords.Common.Regex.Split("[.]",_url.substring((int) (7)));
 //BA.debugLineNum = 594;BA.debugLine="Row = Values(1)";
_row = (int)(Double.parseDouble(_values[(int) (1)]));
 //BA.debugLineNum = 596;BA.debugLine="Dim Val(5) As String";
_val = new String[(int) (5)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 598;BA.debugLine="Val = raresMarketList.Get(Row)";
_val = (String[])(_raresmarketlist.Get(_row));
 //BA.debugLineNum = 600;BA.debugLine="lblTradeRares.Text = \"Places that BUY - \" & Val(1";
mostCurrent._lbltraderares.setText((Object)("Places that BUY - "+_val[(int) (1)]));
 //BA.debugLineNum = 602;BA.debugLine="FillRaresSystems(Val(2))";
_fillraressystems(_val[(int) (2)]);
 //BA.debugLineNum = 604;BA.debugLine="Return True 'Don't try to navigate to this URL";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 606;BA.debugLine="End Sub";
return false;
}
public static boolean  _wbvmarketdemand_overrideurl(String _url) throws Exception{
String[] _values = null;
int _row = 0;
String[] _val = null;
 //BA.debugLineNum = 552;BA.debugLine="Sub wbvMarketDemand_OverrideUrl (Url As String) As";
 //BA.debugLineNum = 554;BA.debugLine="Dim Values() As String";
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 555;BA.debugLine="Dim Row As Int";
_row = 0;
 //BA.debugLineNum = 557;BA.debugLine="Values = Regex.Split(\"[.]\", Url.SubString(7))";
_values = anywheresoftware.b4a.keywords.Common.Regex.Split("[.]",_url.substring((int) (7)));
 //BA.debugLineNum = 558;BA.debugLine="Row = Values(1)";
_row = (int)(Double.parseDouble(_values[(int) (1)]));
 //BA.debugLineNum = 560;BA.debugLine="Dim Val(10) As String";
_val = new String[(int) (10)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 562;BA.debugLine="Val = demandMarketList.Get(Row)";
_val = (String[])(_demandmarketlist.Get(_row));
 //BA.debugLineNum = 564;BA.debugLine="FillTradeSystems(Val(5), Val(4), Val(6), \"TRADE\")";
_filltradesystems(_val[(int) (5)],_val[(int) (4)],(int)(Double.parseDouble(_val[(int) (6)])),"TRADE");
 //BA.debugLineNum = 566;BA.debugLine="lblTradeDem.Text = \"Places that SELL - \" & Val(4)";
mostCurrent._lbltradedem.setText((Object)("Places that SELL - "+_val[(int) (4)]));
 //BA.debugLineNum = 568;BA.debugLine="spnNonItems.SelectedIndex = 0";
mostCurrent._spnnonitems.setSelectedIndex((int) (0));
 //BA.debugLineNum = 570;BA.debugLine="Return True 'Don't try to navigate to this URL";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 572;BA.debugLine="End Sub";
return false;
}
public static boolean  _wbvmarketsupply_overrideurl(String _url) throws Exception{
String[] _values = null;
int _row = 0;
String[] _val = null;
 //BA.debugLineNum = 526;BA.debugLine="Sub wbvMarketSupply_OverrideUrl (Url As String) As";
 //BA.debugLineNum = 528;BA.debugLine="Dim Values() As String";
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 529;BA.debugLine="Dim Row As Int";
_row = 0;
 //BA.debugLineNum = 531;BA.debugLine="Values = Regex.Split(\"[.]\", Url.SubString(7))";
_values = anywheresoftware.b4a.keywords.Common.Regex.Split("[.]",_url.substring((int) (7)));
 //BA.debugLineNum = 532;BA.debugLine="Row = Values(1)";
_row = (int)(Double.parseDouble(_values[(int) (1)]));
 //BA.debugLineNum = 534;BA.debugLine="Dim Val(10) As String";
_val = new String[(int) (10)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 536;BA.debugLine="Val = supplyMarketList.Get(Row)";
_val = (String[])(_supplymarketlist.Get(_row));
 //BA.debugLineNum = 538;BA.debugLine="FillTradeSystems(Val(5), Val(4), Val(6), \"TRADE\")";
_filltradesystems(_val[(int) (5)],_val[(int) (4)],(int)(Double.parseDouble(_val[(int) (6)])),"TRADE");
 //BA.debugLineNum = 540;BA.debugLine="lblTradeSup.Text = \"Places that BUY - \" & Val(4)";
mostCurrent._lbltradesup.setText((Object)("Places that BUY - "+_val[(int) (4)]));
 //BA.debugLineNum = 542;BA.debugLine="spnNonItems.SelectedIndex = 0";
mostCurrent._spnnonitems.setSelectedIndex((int) (0));
 //BA.debugLineNum = 544;BA.debugLine="Return True 'Don't try to navigate to this URL";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 546;BA.debugLine="End Sub";
return false;
}
}
