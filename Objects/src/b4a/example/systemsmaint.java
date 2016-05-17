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

public class systemsmaint extends Activity implements B4AActivity{
	public static systemsmaint mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.systemsmaint");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (systemsmaint).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.systemsmaint");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.systemsmaint", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (systemsmaint) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (systemsmaint) Resume **");
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
		return systemsmaint.class;
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
        BA.LogInfo("** Activity (systemsmaint) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (systemsmaint) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _alphalistsystems = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadd = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncancel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndelete = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpointinspace = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsave = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtsystemname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblx = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbly = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblz = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnallegiance = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spneconomy = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spngovernment = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wbvsystemlist = null;
public static boolean _addoredit = false;
public static boolean _result = false;
public static int _pointexact = 0;
public static String _oldgovernmentdesc = "";
public static String _oldallegdesc = "";
public static String _q = "";
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.dbadmin _dbadmin = null;
public b4a.example.galaxymaint _galaxymaint = null;
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
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="Activity.LoadLayout(\"SystemsMaint\")";
mostCurrent._activity.LoadLayout("SystemsMaint",mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="InitSpinners";
_initspinners();
 //BA.debugLineNum = 52;BA.debugLine="Functions.SetColours(Activity)";
mostCurrent._functions._setcolours(mostCurrent.activityBA,mostCurrent._activity);
 //BA.debugLineNum = 54;BA.debugLine="FillSystemsList";
_fillsystemslist();
 //BA.debugLineNum = 56;BA.debugLine="PointExact = 0";
_pointexact = (int) (0);
 //BA.debugLineNum = 58;BA.debugLine="edtSystemName.Text = \"\"";
mostCurrent._edtsystemname.setText((Object)(""));
 //BA.debugLineNum = 59;BA.debugLine="lblX.Text = \"\"";
mostCurrent._lblx.setText((Object)(""));
 //BA.debugLineNum = 60;BA.debugLine="lblY.Text = \"\"";
mostCurrent._lbly.setText((Object)(""));
 //BA.debugLineNum = 61;BA.debugLine="lblZ.Text = \"\"";
mostCurrent._lblz.setText((Object)(""));
 //BA.debugLineNum = 63;BA.debugLine="edtSystemName.Enabled = False";
mostCurrent._edtsystemname.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="spnGovernment.Enabled = False";
mostCurrent._spngovernment.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 65;BA.debugLine="spnEconomy.Enabled = False";
mostCurrent._spneconomy.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 66;BA.debugLine="spnAllegiance.Enabled = False";
mostCurrent._spnallegiance.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 67;BA.debugLine="btnPointInSpace.Enabled = False";
mostCurrent._btnpointinspace.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="btnCancel.Visible = False";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 70;BA.debugLine="btnDelete.Visible = False";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="btnSave.Visible = False";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _btnadd_click() throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Sub btnAdd_Click";
 //BA.debugLineNum = 184;BA.debugLine="AddOREdit = True 'Add new record";
_addoredit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 186;BA.debugLine="btnAdd.Visible = False";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 187;BA.debugLine="btnCancel.Visible = True";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 188;BA.debugLine="btnSave.Visible = True";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 189;BA.debugLine="edtSystemName.Enabled = True";
mostCurrent._edtsystemname.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 190;BA.debugLine="spnGovernment.Enabled = True";
mostCurrent._spngovernment.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="spnEconomy.Enabled = True";
mostCurrent._spneconomy.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 192;BA.debugLine="spnAllegiance.Enabled = True";
mostCurrent._spnallegiance.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 193;BA.debugLine="btnPointInSpace.Enabled = Starter.AnchorsDefined";
mostCurrent._btnpointinspace.setEnabled(mostCurrent._starter._anchorsdefined);
 //BA.debugLineNum = 194;BA.debugLine="lblX.Text = \"\"";
mostCurrent._lblx.setText((Object)(""));
 //BA.debugLineNum = 195;BA.debugLine="lblY.Text = \"\"";
mostCurrent._lbly.setText((Object)(""));
 //BA.debugLineNum = 196;BA.debugLine="lblZ.Text = \"\"";
mostCurrent._lblz.setText((Object)(""));
 //BA.debugLineNum = 197;BA.debugLine="PointExact = 0";
_pointexact = (int) (0);
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 269;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 270;BA.debugLine="btnCancel.Visible = False";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="btnDelete.Visible = False";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 272;BA.debugLine="btnSave.Visible = False";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 273;BA.debugLine="btnAdd.Visible = True";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 274;BA.debugLine="edtSystemName.Text = \"\"";
mostCurrent._edtsystemname.setText((Object)(""));
 //BA.debugLineNum = 275;BA.debugLine="spnGovernment.SelectedIndex = 0";
mostCurrent._spngovernment.setSelectedIndex((int) (0));
 //BA.debugLineNum = 276;BA.debugLine="spnEconomy.SelectedIndex = 0";
mostCurrent._spneconomy.setSelectedIndex((int) (0));
 //BA.debugLineNum = 277;BA.debugLine="spnAllegiance.SelectedIndex = 0";
mostCurrent._spnallegiance.setSelectedIndex((int) (0));
 //BA.debugLineNum = 278;BA.debugLine="edtSystemName.Enabled = False";
mostCurrent._edtsystemname.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="spnGovernment.Enabled = False";
mostCurrent._spngovernment.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="spnEconomy.Enabled = False";
mostCurrent._spneconomy.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 281;BA.debugLine="spnAllegiance.Enabled = False";
mostCurrent._spnallegiance.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="btnPointInSpace.Enabled = False";
mostCurrent._btnpointinspace.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="lblX.Text = \"\"";
mostCurrent._lblx.setText((Object)(""));
 //BA.debugLineNum = 284;BA.debugLine="lblY.Text = \"\"";
mostCurrent._lbly.setText((Object)(""));
 //BA.debugLineNum = 285;BA.debugLine="lblZ.Text = \"\"";
mostCurrent._lblz.setText((Object)(""));
 //BA.debugLineNum = 286;BA.debugLine="PointExact = 0";
_pointexact = (int) (0);
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _btndelete_click() throws Exception{
int _answ = 0;
int _counter = 0;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
 //BA.debugLineNum = 201;BA.debugLine="Sub btnDelete_Click";
 //BA.debugLineNum = 202;BA.debugLine="Dim Answ As Int";
_answ = 0;
 //BA.debugLineNum = 204;BA.debugLine="Answ = Msgbox2(\"Do you really want to delete this";
_answ = anywheresoftware.b4a.keywords.Common.Msgbox2("Do you really want to delete this System and associated stations & market info ?","DELETE record","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 206;BA.debugLine="If Answ = DialogResponse.POSITIVE Then";
if (_answ==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 208;BA.debugLine="Q = \"DELETE FROM StockMarket WHERE SystemName =";
mostCurrent._q = "DELETE FROM StockMarket WHERE SystemName = ?";
 //BA.debugLineNum = 209;BA.debugLine="Starter.SQLExec.ExecNonQuery2(Q, Array As String";
mostCurrent._starter._sqlexec.ExecNonQuery2(mostCurrent._q,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._edtsystemname.getText()}));
 //BA.debugLineNum = 210;BA.debugLine="Q = \"DELETE FROM Stations WHERE SystemName = ?\"";
mostCurrent._q = "DELETE FROM Stations WHERE SystemName = ?";
 //BA.debugLineNum = 211;BA.debugLine="Starter.SQLExec.ExecNonQuery2(Q, Array As String";
mostCurrent._starter._sqlexec.ExecNonQuery2(mostCurrent._q,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._edtsystemname.getText()}));
 //BA.debugLineNum = 213;BA.debugLine="Dim counter As Int";
_counter = 0;
 //BA.debugLineNum = 214;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 217;BA.debugLine="If oldGovernmentDesc <> \"Not Set\" Then";
if ((mostCurrent._oldgovernmentdesc).equals("Not Set") == false) { 
 //BA.debugLineNum = 218;BA.debugLine="Q = \"SELECT NumOfEntries FROM Governments WHERE";
mostCurrent._q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?";
 //BA.debugLineNum = 219;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResult";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._oldgovernmentdesc})));
 //BA.debugLineNum = 220;BA.debugLine="counter = counter - 1";
_counter = (int) (_counter-1);
 //BA.debugLineNum = 221;BA.debugLine="If counter < 0 Then";
if (_counter<0) { 
 //BA.debugLineNum = 222;BA.debugLine="counter = 0";
_counter = (int) (0);
 };
 //BA.debugLineNum = 224;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 225;BA.debugLine="whereclause.Put(\"GovDesc\", oldGovernmentDesc)";
_whereclause.Put((Object)("GovDesc"),(Object)(mostCurrent._oldgovernmentdesc));
 //BA.debugLineNum = 226;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Governmen";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Governments","NumOfEntries",(Object)(_counter),_whereclause);
 };
 //BA.debugLineNum = 230;BA.debugLine="If oldAllegDesc <> \"Not Set\" Then";
if ((mostCurrent._oldallegdesc).equals("Not Set") == false) { 
 //BA.debugLineNum = 231;BA.debugLine="Q = \"SELECT NumOfEntries FROM Allegiance WHERE";
mostCurrent._q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?";
 //BA.debugLineNum = 232;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResult";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._oldallegdesc})));
 //BA.debugLineNum = 233;BA.debugLine="counter = counter - 1";
_counter = (int) (_counter-1);
 //BA.debugLineNum = 234;BA.debugLine="If counter < 0 Then";
if (_counter<0) { 
 //BA.debugLineNum = 235;BA.debugLine="counter = 0";
_counter = (int) (0);
 };
 //BA.debugLineNum = 237;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 238;BA.debugLine="whereclause.Put(\"AllegDesc\", oldAllegDesc)";
_whereclause.Put((Object)("AllegDesc"),(Object)(mostCurrent._oldallegdesc));
 //BA.debugLineNum = 239;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Allegianc";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Allegiance","NumOfEntries",(Object)(_counter),_whereclause);
 };
 //BA.debugLineNum = 242;BA.debugLine="Dim mp As Map";
_mp = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 243;BA.debugLine="mp.Initialize";
_mp.Initialize();
 //BA.debugLineNum = 244;BA.debugLine="mp.Put(\"SystemName\", edtSystemName.Text)";
_mp.Put((Object)("SystemName"),(Object)(mostCurrent._edtsystemname.getText()));
 //BA.debugLineNum = 245;BA.debugLine="DBUtils.DeleteRecord(Starter.SQLExec, \"Systems\",";
mostCurrent._dbutils._deleterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Systems",_mp);
 //BA.debugLineNum = 247;BA.debugLine="FillSystemsList";
_fillsystemslist();
 };
 //BA.debugLineNum = 250;BA.debugLine="btnDelete.Visible = False";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="btnCancel.Visible = False";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="btnSave.Visible = False";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="btnAdd.Visible = True";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 254;BA.debugLine="edtSystemName.Text = \"\"";
mostCurrent._edtsystemname.setText((Object)(""));
 //BA.debugLineNum = 255;BA.debugLine="spnGovernment.SelectedIndex = 0";
mostCurrent._spngovernment.setSelectedIndex((int) (0));
 //BA.debugLineNum = 256;BA.debugLine="spnEconomy.SelectedIndex = 0";
mostCurrent._spneconomy.setSelectedIndex((int) (0));
 //BA.debugLineNum = 257;BA.debugLine="spnAllegiance.SelectedIndex = 0";
mostCurrent._spnallegiance.setSelectedIndex((int) (0));
 //BA.debugLineNum = 258;BA.debugLine="edtSystemName.Enabled = False";
mostCurrent._edtsystemname.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="spnGovernment.Enabled = False";
mostCurrent._spngovernment.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 260;BA.debugLine="spnEconomy.Enabled = False";
mostCurrent._spneconomy.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 261;BA.debugLine="spnAllegiance.Enabled = False";
mostCurrent._spnallegiance.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 262;BA.debugLine="btnPointInSpace.Enabled = False";
mostCurrent._btnpointinspace.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 263;BA.debugLine="lblX.Text = \"\"";
mostCurrent._lblx.setText((Object)(""));
 //BA.debugLineNum = 264;BA.debugLine="lblY.Text = \"\"";
mostCurrent._lbly.setText((Object)(""));
 //BA.debugLineNum = 265;BA.debugLine="lblZ.Text = \"\"";
mostCurrent._lblz.setText((Object)(""));
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _btnexit_click() throws Exception{
 //BA.debugLineNum = 427;BA.debugLine="Sub btnExit_Click";
 //BA.debugLineNum = 428;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _btnpointinspace_click() throws Exception{
b4a.example.coordinate _systemlocation = null;
 //BA.debugLineNum = 161;BA.debugLine="Sub btnPointInSpace_Click";
 //BA.debugLineNum = 163;BA.debugLine="Dim systemlocation As Coordinate";
_systemlocation = new b4a.example.coordinate();
 //BA.debugLineNum = 164;BA.debugLine="systemlocation = Null";
_systemlocation = (b4a.example.coordinate)(anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 166;BA.debugLine="btnPointInSpace.RequestFocus";
mostCurrent._btnpointinspace.RequestFocus();
 //BA.debugLineNum = 168;BA.debugLine="systemlocation = Elite.PlotNewSystem";
_systemlocation = mostCurrent._elite._plotnewsystem(mostCurrent.activityBA);
 //BA.debugLineNum = 170;BA.debugLine="If systemlocation = Null Then";
if (_systemlocation== null) { 
 //BA.debugLineNum = 171;BA.debugLine="lblX.Text = \"\"";
mostCurrent._lblx.setText((Object)(""));
 //BA.debugLineNum = 172;BA.debugLine="lblY.Text = \"\"";
mostCurrent._lbly.setText((Object)(""));
 //BA.debugLineNum = 173;BA.debugLine="lblZ.Text = \"\"";
mostCurrent._lblz.setText((Object)(""));
 }else {
 //BA.debugLineNum = 175;BA.debugLine="lblX.Text = Round2(systemlocation.X,5)";
mostCurrent._lblx.setText((Object)(anywheresoftware.b4a.keywords.Common.Round2(_systemlocation._getx(),(int) (5))));
 //BA.debugLineNum = 176;BA.debugLine="lblY.Text = Round2(systemlocation.Y,5)";
mostCurrent._lbly.setText((Object)(anywheresoftware.b4a.keywords.Common.Round2(_systemlocation._gety(),(int) (5))));
 //BA.debugLineNum = 177;BA.debugLine="lblZ.Text = Round2(systemlocation.Z,5)";
mostCurrent._lblz.setText((Object)(anywheresoftware.b4a.keywords.Common.Round2(_systemlocation._getz(),(int) (5))));
 //BA.debugLineNum = 178;BA.debugLine="PointExact = 1";
_pointexact = (int) (1);
 };
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
int _counter = 0;
anywheresoftware.b4a.objects.collections.Map _record = null;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 290;BA.debugLine="Sub btnSave_Click";
 //BA.debugLineNum = 291;BA.debugLine="Dim counter As Int";
_counter = 0;
 //BA.debugLineNum = 292;BA.debugLine="If AddOREdit = True Then";
if (_addoredit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 294;BA.debugLine="If edtSystemName.Text.Length > 0 Then";
if (mostCurrent._edtsystemname.getText().length()>0) { 
 //BA.debugLineNum = 296;BA.debugLine="If Elite.SystemExists(edtSystemName.Text) = Fal";
if (mostCurrent._elite._systemexists(mostCurrent.activityBA,mostCurrent._edtsystemname.getText())==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 297;BA.debugLine="Dim record As Map, whereclause As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 299;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 300;BA.debugLine="record.Put(\"SystemName\", edtSystemName.Text)";
_record.Put((Object)("SystemName"),(Object)(mostCurrent._edtsystemname.getText()));
 //BA.debugLineNum = 301;BA.debugLine="record.Put(\"GovDesc\", spnGovernment.GetItem(sp";
_record.Put((Object)("GovDesc"),(Object)(mostCurrent._spngovernment.GetItem(mostCurrent._spngovernment.getSelectedIndex())));
 //BA.debugLineNum = 302;BA.debugLine="record.Put(\"EconomyID\", Elite.FindEconomyNum(s";
_record.Put((Object)("EconomyID"),(Object)(mostCurrent._elite._findeconomynum(mostCurrent.activityBA,mostCurrent._spneconomy.GetItem(mostCurrent._spneconomy.getSelectedIndex()))));
 //BA.debugLineNum = 303;BA.debugLine="record.Put(\"AllegDesc\", spnAllegiance.GetItem(";
_record.Put((Object)("AllegDesc"),(Object)(mostCurrent._spnallegiance.GetItem(mostCurrent._spnallegiance.getSelectedIndex())));
 //BA.debugLineNum = 304;BA.debugLine="record.Put(\"SpaceX\", lblX.Text)";
_record.Put((Object)("SpaceX"),(Object)(mostCurrent._lblx.getText()));
 //BA.debugLineNum = 305;BA.debugLine="record.Put(\"SpaceY\", lblY.Text)";
_record.Put((Object)("SpaceY"),(Object)(mostCurrent._lbly.getText()));
 //BA.debugLineNum = 306;BA.debugLine="record.Put(\"SpaceZ\", lblZ.Text)";
_record.Put((Object)("SpaceZ"),(Object)(mostCurrent._lblz.getText()));
 //BA.debugLineNum = 307;BA.debugLine="record.Put(\"LYfromCurrent\", 0)";
_record.Put((Object)("LYfromCurrent"),(Object)(0));
 //BA.debugLineNum = 308;BA.debugLine="record.Put(\"ExactLocation\", PointExact)";
_record.Put((Object)("ExactLocation"),(Object)(_pointexact));
 //BA.debugLineNum = 309;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"Syst";
mostCurrent._sqlutils._table_insertmap(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Systems",_record);
 //BA.debugLineNum = 312;BA.debugLine="If spnGovernment.SelectedItem <> \"Not Set\" The";
if ((mostCurrent._spngovernment.getSelectedItem()).equals("Not Set") == false) { 
 //BA.debugLineNum = 313;BA.debugLine="Q = \"SELECT NumOfEntries FROM Governments WHE";
mostCurrent._q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?";
 //BA.debugLineNum = 314;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._spngovernment.getSelectedItem()})));
 //BA.debugLineNum = 316;BA.debugLine="counter = counter + 1";
_counter = (int) (_counter+1);
 //BA.debugLineNum = 318;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 319;BA.debugLine="whereclause.Put(\"GovDesc\", spnGovernment.Sele";
_whereclause.Put((Object)("GovDesc"),(Object)(mostCurrent._spngovernment.getSelectedItem()));
 //BA.debugLineNum = 320;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Governm";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Governments","NumOfEntries",(Object)(_counter),_whereclause);
 };
 //BA.debugLineNum = 323;BA.debugLine="If spnAllegiance.SelectedItem <> \"Not Set\" The";
if ((mostCurrent._spnallegiance.getSelectedItem()).equals("Not Set") == false) { 
 //BA.debugLineNum = 325;BA.debugLine="Q = \"SELECT NumOfEntries FROM Allegiance WHER";
mostCurrent._q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?";
 //BA.debugLineNum = 326;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._spnallegiance.getSelectedItem()})));
 //BA.debugLineNum = 328;BA.debugLine="counter = counter + 1";
_counter = (int) (_counter+1);
 //BA.debugLineNum = 330;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 331;BA.debugLine="whereclause.Put(\"AllegDesc\", spnAllegiance.Se";
_whereclause.Put((Object)("AllegDesc"),(Object)(mostCurrent._spnallegiance.getSelectedItem()));
 //BA.debugLineNum = 332;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Allegia";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Allegiance","NumOfEntries",(Object)(_counter),_whereclause);
 };
 };
 //BA.debugLineNum = 336;BA.debugLine="result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 338;BA.debugLine="Msgbox(\"Must enter a name for the Star System b";
anywheresoftware.b4a.keywords.Common.Msgbox("Must enter a name for the Star System before save is possible","A T T E N T I O N",mostCurrent.activityBA);
 //BA.debugLineNum = 339;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 }else {
 //BA.debugLineNum = 343;BA.debugLine="If edtSystemName.Text.Length > 0 Then";
if (mostCurrent._edtsystemname.getText().length()>0) { 
 //BA.debugLineNum = 344;BA.debugLine="If Elite.SystemExists(edtSystemName.Text) = Tru";
if (mostCurrent._elite._systemexists(mostCurrent.activityBA,mostCurrent._edtsystemname.getText())==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 345;BA.debugLine="Dim record As Map, whereclause As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 347;BA.debugLine="If oldGovernmentDesc <> spnGovernment.Selected";
if ((mostCurrent._oldgovernmentdesc).equals(mostCurrent._spngovernment.getSelectedItem()) == false && (mostCurrent._oldgovernmentdesc).equals("Not Set") == false) { 
 //BA.debugLineNum = 349;BA.debugLine="Q = \"SELECT NumOfEntries FROM Governments WHE";
mostCurrent._q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?";
 //BA.debugLineNum = 350;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._oldgovernmentdesc})));
 //BA.debugLineNum = 351;BA.debugLine="counter = counter - 1";
_counter = (int) (_counter-1);
 //BA.debugLineNum = 352;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 353;BA.debugLine="whereclause.Put(\"GovDesc\", oldGovernmentDesc)";
_whereclause.Put((Object)("GovDesc"),(Object)(mostCurrent._oldgovernmentdesc));
 //BA.debugLineNum = 354;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Governm";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Governments","NumOfEntries",(Object)(_counter),_whereclause);
 //BA.debugLineNum = 356;BA.debugLine="Q = \"SELECT NumOfEntries FROM Governments WHE";
mostCurrent._q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?";
 //BA.debugLineNum = 357;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._spngovernment.getSelectedItem()})));
 //BA.debugLineNum = 358;BA.debugLine="counter = counter + 1";
_counter = (int) (_counter+1);
 //BA.debugLineNum = 359;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 360;BA.debugLine="whereclause.Put(\"GovDesc\", spnGovernment.Sele";
_whereclause.Put((Object)("GovDesc"),(Object)(mostCurrent._spngovernment.getSelectedItem()));
 //BA.debugLineNum = 361;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Governm";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Governments","NumOfEntries",(Object)(_counter),_whereclause);
 };
 //BA.debugLineNum = 364;BA.debugLine="If oldAllegDesc <> spnAllegiance.SelectedItem";
if ((mostCurrent._oldallegdesc).equals(mostCurrent._spnallegiance.getSelectedItem()) == false && (mostCurrent._oldallegdesc).equals("Not Set") == false) { 
 //BA.debugLineNum = 366;BA.debugLine="Q = \"SELECT NumOfEntries FROM Allegiance WHER";
mostCurrent._q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?";
 //BA.debugLineNum = 367;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._oldallegdesc})));
 //BA.debugLineNum = 368;BA.debugLine="counter = counter - 1";
_counter = (int) (_counter-1);
 //BA.debugLineNum = 369;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 370;BA.debugLine="whereclause.Put(\"AllegDesc\", oldAllegDesc)";
_whereclause.Put((Object)("AllegDesc"),(Object)(mostCurrent._oldallegdesc));
 //BA.debugLineNum = 371;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Allegia";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Allegiance","NumOfEntries",(Object)(_counter),_whereclause);
 //BA.debugLineNum = 373;BA.debugLine="Q = \"SELECT NumOfEntries FROM Allegiance WHER";
mostCurrent._q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?";
 //BA.debugLineNum = 374;BA.debugLine="counter = Starter.SQLExec.ExecQuerySingleResu";
_counter = (int)(Double.parseDouble(mostCurrent._starter._sqlexec.ExecQuerySingleResult2(mostCurrent._q,new String[]{mostCurrent._spnallegiance.getSelectedItem()})));
 //BA.debugLineNum = 375;BA.debugLine="counter = counter + 1";
_counter = (int) (_counter+1);
 //BA.debugLineNum = 376;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 377;BA.debugLine="whereclause.Put(\"AllegDesc\", spnAllegiance.Se";
_whereclause.Put((Object)("AllegDesc"),(Object)(mostCurrent._spnallegiance.getSelectedItem()));
 //BA.debugLineNum = 378;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec,\"Allegia";
mostCurrent._dbutils._updaterecord(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Allegiance","NumOfEntries",(Object)(_counter),_whereclause);
 };
 //BA.debugLineNum = 381;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 382;BA.debugLine="whereclause.Put(\"SystemName\", edtSystemName.Te";
_whereclause.Put((Object)("SystemName"),(Object)(mostCurrent._edtsystemname.getText()));
 //BA.debugLineNum = 383;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 384;BA.debugLine="record.Put(\"GovDesc\", spnGovernment.GetItem(sp";
_record.Put((Object)("GovDesc"),(Object)(mostCurrent._spngovernment.GetItem(mostCurrent._spngovernment.getSelectedIndex())));
 //BA.debugLineNum = 385;BA.debugLine="record.Put(\"EconomyID\", Elite.FindEconomyNum(s";
_record.Put((Object)("EconomyID"),(Object)(mostCurrent._elite._findeconomynum(mostCurrent.activityBA,mostCurrent._spneconomy.GetItem(mostCurrent._spneconomy.getSelectedIndex()))));
 //BA.debugLineNum = 386;BA.debugLine="record.Put(\"AllegDesc\", spnAllegiance.GetItem(";
_record.Put((Object)("AllegDesc"),(Object)(mostCurrent._spnallegiance.GetItem(mostCurrent._spnallegiance.getSelectedIndex())));
 //BA.debugLineNum = 387;BA.debugLine="record.Put(\"SpaceX\", lblX.Text)";
_record.Put((Object)("SpaceX"),(Object)(mostCurrent._lblx.getText()));
 //BA.debugLineNum = 388;BA.debugLine="record.Put(\"SpaceY\", lblY.Text)";
_record.Put((Object)("SpaceY"),(Object)(mostCurrent._lbly.getText()));
 //BA.debugLineNum = 389;BA.debugLine="record.Put(\"SpaceZ\", lblZ.Text)";
_record.Put((Object)("SpaceZ"),(Object)(mostCurrent._lblz.getText()));
 //BA.debugLineNum = 390;BA.debugLine="record.Put(\"ExactLocation\", PointExact)";
_record.Put((Object)("ExactLocation"),(Object)(_pointexact));
 //BA.debugLineNum = 391;BA.debugLine="DBUtils.UpdateRecord2(Starter.SQLExec,\"Systems";
mostCurrent._dbutils._updaterecord2(mostCurrent.activityBA,mostCurrent._starter._sqlexec,"Systems",_record,_whereclause);
 }else {
 //BA.debugLineNum = 393;BA.debugLine="Msgbox(\"Star System name can not be changed\",";
anywheresoftware.b4a.keywords.Common.Msgbox("Star System name can not be changed","A T T E N T I O N",mostCurrent.activityBA);
 //BA.debugLineNum = 394;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 396;BA.debugLine="result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 398;BA.debugLine="Msgbox(\"Star System name can not be blank\", \"A";
anywheresoftware.b4a.keywords.Common.Msgbox("Star System name can not be blank","A T T E N T I O N",mostCurrent.activityBA);
 //BA.debugLineNum = 399;BA.debugLine="result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 //BA.debugLineNum = 403;BA.debugLine="If result = True Then";
if (_result==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 405;BA.debugLine="btnSave.Visible = False";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 406;BA.debugLine="btnDelete.Visible = False";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 407;BA.debugLine="btnCancel.Visible = False";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 408;BA.debugLine="btnAdd.Visible = True";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 409;BA.debugLine="edtSystemName.Text = \"\"";
mostCurrent._edtsystemname.setText((Object)(""));
 //BA.debugLineNum = 410;BA.debugLine="spnGovernment.SelectedIndex = 0";
mostCurrent._spngovernment.setSelectedIndex((int) (0));
 //BA.debugLineNum = 411;BA.debugLine="spnEconomy.SelectedIndex = 0";
mostCurrent._spneconomy.setSelectedIndex((int) (0));
 //BA.debugLineNum = 412;BA.debugLine="spnAllegiance.SelectedIndex = 0";
mostCurrent._spnallegiance.setSelectedIndex((int) (0));
 //BA.debugLineNum = 413;BA.debugLine="edtSystemName.Enabled = False";
mostCurrent._edtsystemname.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 414;BA.debugLine="spnGovernment.Enabled = False";
mostCurrent._spngovernment.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 415;BA.debugLine="spnEconomy.Enabled = False";
mostCurrent._spneconomy.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 416;BA.debugLine="spnAllegiance.Enabled = False";
mostCurrent._spnallegiance.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 417;BA.debugLine="btnPointInSpace.Enabled = False";
mostCurrent._btnpointinspace.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 418;BA.debugLine="lblX.Text = \"\"";
mostCurrent._lblx.setText((Object)(""));
 //BA.debugLineNum = 419;BA.debugLine="lblY.Text = \"\"";
mostCurrent._lbly.setText((Object)(""));
 //BA.debugLineNum = 420;BA.debugLine="lblZ.Text = \"\"";
mostCurrent._lblz.setText((Object)(""));
 //BA.debugLineNum = 422;BA.debugLine="FillSystemsList";
_fillsystemslist();
 };
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return "";
}
public static String  _edtsystemname_enterpressed() throws Exception{
 //BA.debugLineNum = 501;BA.debugLine="Sub edtSystemName_EnterPressed";
 //BA.debugLineNum = 502;BA.debugLine="edtSystemName.Text = edtSystemName.Text";
mostCurrent._edtsystemname.setText((Object)(mostCurrent._edtsystemname.getText()));
 //BA.debugLineNum = 503;BA.debugLine="If Elite.SystemExists(edtSystemName.Text) = True";
if (mostCurrent._elite._systemexists(mostCurrent.activityBA,mostCurrent._edtsystemname.getText())==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 504;BA.debugLine="edtSystemName.Text = \"\"";
mostCurrent._edtsystemname.setText((Object)(""));
 //BA.debugLineNum = 505;BA.debugLine="edtSystemName.RequestFocus";
mostCurrent._edtsystemname.RequestFocus();
 };
 //BA.debugLineNum = 508;BA.debugLine="End Sub";
return "";
}
public static String  _fillsystemslist() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub FillSystemsList";
 //BA.debugLineNum = 150;BA.debugLine="Q = \"SELECT S.SystemName AS [Star System], E.Econ";
mostCurrent._q = "SELECT S.SystemName AS [Star System], E.EconomyDesc AS [Economy], S.AllegDesc AS [Allegiance], S.GovDesc AS [Government], S.LYfromCurrent As [LY] FROM Systems S LEFT JOIN Economies E ON S.EconomyID = E.EconomyID ORDER BY S.LYfromCurrent ASC, S.SystemName ASC";
 //BA.debugLineNum = 151;BA.debugLine="wbvSystemList.LoadHtml(DBUtils.ExecuteHtml(Starte";
mostCurrent._wbvsystemlist.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0),anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 153;BA.debugLine="SystemsListAlpha 'Sets the ID lookup list of the";
_systemslistalpha();
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnAdd As Button";
mostCurrent._btnadd = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnCancel As Button";
mostCurrent._btncancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnDelete As Button";
mostCurrent._btndelete = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnPointInSpace As Button";
mostCurrent._btnpointinspace = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnSave As Button";
mostCurrent._btnsave = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private edtSystemName As EditText";
mostCurrent._edtsystemname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblX As Label";
mostCurrent._lblx = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblY As Label";
mostCurrent._lbly = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblZ As Label";
mostCurrent._lblz = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private spnAllegiance As Spinner";
mostCurrent._spnallegiance = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private spnEconomy As Spinner";
mostCurrent._spneconomy = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private spnGovernment As Spinner";
mostCurrent._spngovernment = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private wbvSystemList As WebView";
mostCurrent._wbvsystemlist = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim AddOREdit As Boolean 'True equals Add, False";
_addoredit = false;
 //BA.debugLineNum = 36;BA.debugLine="Dim result As Boolean";
_result = false;
 //BA.debugLineNum = 38;BA.debugLine="Dim PointExact As Int";
_pointexact = 0;
 //BA.debugLineNum = 40;BA.debugLine="Dim oldGovernmentDesc As String";
mostCurrent._oldgovernmentdesc = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim oldAllegDesc As String";
mostCurrent._oldallegdesc = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim Q As String";
mostCurrent._q = "";
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _initspinners() throws Exception{
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursgovernments = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curseconomies = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursallegiances = null;
 //BA.debugLineNum = 85;BA.debugLine="Sub InitSpinners";
 //BA.debugLineNum = 86;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 87;BA.debugLine="Dim CursGovernments As Cursor, CursEconomies As C";
_cursgovernments = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_curseconomies = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursallegiances = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Q = \"SELECT * FROM Governments ORDER BY NumOfEntr";
mostCurrent._q = "SELECT * FROM Governments ORDER BY NumOfEntries DESC, GovDesc ASC";
 //BA.debugLineNum = 91;BA.debugLine="CursGovernments = Starter.SQLExec.ExecQuery(Q)";
_cursgovernments.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 92;BA.debugLine="If CursGovernments.RowCount > 0 Then";
if (_cursgovernments.getRowCount()>0) { 
 //BA.debugLineNum = 93;BA.debugLine="spnGovernment.Clear";
mostCurrent._spngovernment.Clear();
 //BA.debugLineNum = 94;BA.debugLine="spnGovernment.Add(\"Not Set\")";
mostCurrent._spngovernment.Add("Not Set");
 //BA.debugLineNum = 95;BA.debugLine="For i = 0 To CursGovernments.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (_cursgovernments.getRowCount()-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 96;BA.debugLine="CursGovernments.Position = i";
_cursgovernments.setPosition(_i);
 //BA.debugLineNum = 97;BA.debugLine="spnGovernment.Add(CursGovernments.GetString(\"Go";
mostCurrent._spngovernment.Add(_cursgovernments.GetString("GovDesc"));
 }
};
 }else {
 //BA.debugLineNum = 100;BA.debugLine="spnGovernment.Clear";
mostCurrent._spngovernment.Clear();
 //BA.debugLineNum = 101;BA.debugLine="spnGovernment.Add(\"Empty\")";
mostCurrent._spngovernment.Add("Empty");
 };
 //BA.debugLineNum = 103;BA.debugLine="spnGovernment.SelectedIndex = 0";
mostCurrent._spngovernment.setSelectedIndex((int) (0));
 //BA.debugLineNum = 104;BA.debugLine="CursGovernments.Close";
_cursgovernments.Close();
 //BA.debugLineNum = 107;BA.debugLine="Q = \"SELECT * FROM Economies WHERE Surface = 0 OR";
mostCurrent._q = "SELECT * FROM Economies WHERE Surface = 0 ORDER BY NumOfEntries DESC, EconomyDesc ASC";
 //BA.debugLineNum = 108;BA.debugLine="CursEconomies = Starter.SQLExec.ExecQuery(Q)";
_curseconomies.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 109;BA.debugLine="If CursEconomies.RowCount > 0 Then";
if (_curseconomies.getRowCount()>0) { 
 //BA.debugLineNum = 110;BA.debugLine="spnEconomy.Clear";
mostCurrent._spneconomy.Clear();
 //BA.debugLineNum = 111;BA.debugLine="spnEconomy.Add(\"Not Set\")";
mostCurrent._spneconomy.Add("Not Set");
 //BA.debugLineNum = 112;BA.debugLine="For i = 0 To CursEconomies.RowCount - 1";
{
final int step23 = 1;
final int limit23 = (int) (_curseconomies.getRowCount()-1);
for (_i = (int) (0) ; (step23 > 0 && _i <= limit23) || (step23 < 0 && _i >= limit23); _i = ((int)(0 + _i + step23)) ) {
 //BA.debugLineNum = 113;BA.debugLine="CursEconomies.Position = i";
_curseconomies.setPosition(_i);
 //BA.debugLineNum = 114;BA.debugLine="spnEconomy.Add(CursEconomies.GetString(\"Economy";
mostCurrent._spneconomy.Add(_curseconomies.GetString("EconomyDesc"));
 }
};
 }else {
 //BA.debugLineNum = 117;BA.debugLine="spnEconomy.Clear";
mostCurrent._spneconomy.Clear();
 //BA.debugLineNum = 118;BA.debugLine="spnEconomy.Add(\"Empty\")";
mostCurrent._spneconomy.Add("Empty");
 };
 //BA.debugLineNum = 120;BA.debugLine="spnEconomy.SelectedIndex = 0";
mostCurrent._spneconomy.setSelectedIndex((int) (0));
 //BA.debugLineNum = 121;BA.debugLine="CursEconomies.Close";
_curseconomies.Close();
 //BA.debugLineNum = 124;BA.debugLine="Q = \"SELECT * FROM Allegiance ORDER BY NumOfEntri";
mostCurrent._q = "SELECT * FROM Allegiance ORDER BY NumOfEntries DESC, AllegDesc ASC";
 //BA.debugLineNum = 125;BA.debugLine="CursAllegiances = Starter.SQLExec.ExecQuery(Q)";
_cursallegiances.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(mostCurrent._q)));
 //BA.debugLineNum = 126;BA.debugLine="If CursAllegiances.RowCount > 0 Then";
if (_cursallegiances.getRowCount()>0) { 
 //BA.debugLineNum = 127;BA.debugLine="spnAllegiance.Clear";
mostCurrent._spnallegiance.Clear();
 //BA.debugLineNum = 128;BA.debugLine="spnAllegiance.Add(\"Not Set\")";
mostCurrent._spnallegiance.Add("Not Set");
 //BA.debugLineNum = 129;BA.debugLine="For i = 0 To CursAllegiances.RowCount - 1";
{
final int step38 = 1;
final int limit38 = (int) (_cursallegiances.getRowCount()-1);
for (_i = (int) (0) ; (step38 > 0 && _i <= limit38) || (step38 < 0 && _i >= limit38); _i = ((int)(0 + _i + step38)) ) {
 //BA.debugLineNum = 130;BA.debugLine="CursAllegiances.Position = i";
_cursallegiances.setPosition(_i);
 //BA.debugLineNum = 131;BA.debugLine="spnAllegiance.Add(CursAllegiances.GetString(\"Al";
mostCurrent._spnallegiance.Add(_cursallegiances.GetString("AllegDesc"));
 }
};
 }else {
 //BA.debugLineNum = 134;BA.debugLine="spnAllegiance.Clear";
mostCurrent._spnallegiance.Clear();
 //BA.debugLineNum = 135;BA.debugLine="spnAllegiance.Add(\"Empty\")";
mostCurrent._spnallegiance.Add("Empty");
 };
 //BA.debugLineNum = 137;BA.debugLine="spnAllegiance.SelectedIndex = 0";
mostCurrent._spnallegiance.setSelectedIndex((int) (0));
 //BA.debugLineNum = 138;BA.debugLine="CursAllegiances.Close";
_cursallegiances.Close();
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim alphalistSystems As List";
_alphalistsystems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _systemslistalpha() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub SystemsListAlpha";
 //BA.debugLineNum = 143;BA.debugLine="Q = \"SELECT * FROM Systems ORDER BY LYfromCurrent";
mostCurrent._q = "SELECT * FROM Systems ORDER BY LYfromCurrent ASC, SystemName ASC";
 //BA.debugLineNum = 145;BA.debugLine="alphalistSystems = DBUtils.ExecuteMemoryTable(Sta";
_alphalistsystems = mostCurrent._dbutils._executememorytable(mostCurrent.activityBA,mostCurrent._starter._sqlexec,mostCurrent._q,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static boolean  _wbvsystemlist_overrideurl(String _url) throws Exception{
String[] _values = null;
int _row = 0;
String[] _val = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curs = null;
b4a.example.coordinate _systempoint = null;
 //BA.debugLineNum = 434;BA.debugLine="Sub wbvSystemList_OverrideUrl (Url As String) As B";
 //BA.debugLineNum = 436;BA.debugLine="Dim Values() As String";
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 437;BA.debugLine="Dim Row As Int";
_row = 0;
 //BA.debugLineNum = 439;BA.debugLine="Values = Regex.Split(\"[.]\", Url.SubString(7))";
_values = anywheresoftware.b4a.keywords.Common.Regex.Split("[.]",_url.substring((int) (7)));
 //BA.debugLineNum = 440;BA.debugLine="Row = Values(1)";
_row = (int)(Double.parseDouble(_values[(int) (1)]));
 //BA.debugLineNum = 442;BA.debugLine="Dim Val(8) As String";
_val = new String[(int) (8)];
java.util.Arrays.fill(_val,"");
 //BA.debugLineNum = 444;BA.debugLine="Val = alphalistSystems.Get(Row)";
_val = (String[])(_alphalistsystems.Get(_row));
 //BA.debugLineNum = 446;BA.debugLine="edtSystemName.Text = Val(0)";
mostCurrent._edtsystemname.setText((Object)(_val[(int) (0)]));
 //BA.debugLineNum = 447;BA.debugLine="spnGovernment.SelectedIndex = spnGovernment.Index";
mostCurrent._spngovernment.setSelectedIndex(mostCurrent._spngovernment.IndexOf(_val[(int) (1)]));
 //BA.debugLineNum = 448;BA.debugLine="spnAllegiance.SelectedIndex = spnAllegiance.Index";
mostCurrent._spnallegiance.setSelectedIndex(mostCurrent._spnallegiance.IndexOf(_val[(int) (2)]));
 //BA.debugLineNum = 449;BA.debugLine="spnEconomy.SelectedIndex = spnEconomy.IndexOf(Eli";
mostCurrent._spneconomy.setSelectedIndex(mostCurrent._spneconomy.IndexOf(mostCurrent._elite._findeconomy(mostCurrent.activityBA,(int)(Double.parseDouble(_val[(int) (3)])))));
 //BA.debugLineNum = 452;BA.debugLine="Dim Curs As Cursor";
_curs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 453;BA.debugLine="Dim SystemPoint As Coordinate";
_systempoint = new b4a.example.coordinate();
 //BA.debugLineNum = 454;BA.debugLine="Q = \"SELECT * FROM Systems WHERE SystemName = ?\"";
mostCurrent._q = "SELECT * FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 455;BA.debugLine="Curs = Starter.SQLExec.ExecQuery2(Q,Array As Stri";
_curs.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(mostCurrent._q,new String[]{_val[(int) (0)]})));
 //BA.debugLineNum = 456;BA.debugLine="If Curs.RowCount > 0 Then";
if (_curs.getRowCount()>0) { 
 //BA.debugLineNum = 457;BA.debugLine="Curs.Position = 0";
_curs.setPosition((int) (0));
 //BA.debugLineNum = 458;BA.debugLine="SystemPoint.Initialize(Curs.GetDouble(\"SpaceX\"),";
_systempoint._initialize(processBA,_curs.GetDouble("SpaceX"),_curs.GetDouble("SpaceY"),_curs.GetDouble("SpaceZ"));
 //BA.debugLineNum = 459;BA.debugLine="lblX.Text = SystemPoint.X";
mostCurrent._lblx.setText((Object)(_systempoint._getx()));
 //BA.debugLineNum = 460;BA.debugLine="lblY.Text = SystemPoint.Y";
mostCurrent._lbly.setText((Object)(_systempoint._gety()));
 //BA.debugLineNum = 461;BA.debugLine="lblZ.Text = SystemPoint.Z";
mostCurrent._lblz.setText((Object)(_systempoint._getz()));
 }else {
 //BA.debugLineNum = 463;BA.debugLine="lblX.Text = Val(4)";
mostCurrent._lblx.setText((Object)(_val[(int) (4)]));
 //BA.debugLineNum = 464;BA.debugLine="lblY.Text = Val(5)";
mostCurrent._lbly.setText((Object)(_val[(int) (5)]));
 //BA.debugLineNum = 465;BA.debugLine="lblZ.Text = Val(6)";
mostCurrent._lblz.setText((Object)(_val[(int) (6)]));
 };
 //BA.debugLineNum = 468;BA.debugLine="If Val(1).Length < 1 Then";
if (_val[(int) (1)].length()<1) { 
 //BA.debugLineNum = 469;BA.debugLine="oldGovernmentDesc = \"Not Set\"";
mostCurrent._oldgovernmentdesc = "Not Set";
 }else {
 //BA.debugLineNum = 471;BA.debugLine="oldGovernmentDesc = Val(1)";
mostCurrent._oldgovernmentdesc = _val[(int) (1)];
 };
 //BA.debugLineNum = 473;BA.debugLine="If Val(2).Length < 1 Then";
if (_val[(int) (2)].length()<1) { 
 //BA.debugLineNum = 474;BA.debugLine="oldAllegDesc = \"Not Set\"";
mostCurrent._oldallegdesc = "Not Set";
 }else {
 //BA.debugLineNum = 476;BA.debugLine="oldAllegDesc = Val(2)";
mostCurrent._oldallegdesc = _val[(int) (2)];
 };
 //BA.debugLineNum = 479;BA.debugLine="AddOREdit = False 'Edit existing record";
_addoredit = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 481;BA.debugLine="btnAdd.Visible = False";
mostCurrent._btnadd.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 482;BA.debugLine="btnDelete.Visible = True";
mostCurrent._btndelete.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 483;BA.debugLine="btnCancel.Visible = True";
mostCurrent._btncancel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 484;BA.debugLine="btnSave.Visible = True";
mostCurrent._btnsave.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 485;BA.debugLine="edtSystemName.Enabled = True";
mostCurrent._edtsystemname.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 486;BA.debugLine="spnGovernment.Enabled = True";
mostCurrent._spngovernment.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 487;BA.debugLine="spnEconomy.Enabled = True";
mostCurrent._spneconomy.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 488;BA.debugLine="spnAllegiance.Enabled = True";
mostCurrent._spnallegiance.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 490;BA.debugLine="PointExact = Val(8)";
_pointexact = (int)(Double.parseDouble(_val[(int) (8)]));
 //BA.debugLineNum = 491;BA.debugLine="If PointExact = 0 Then";
if (_pointexact==0) { 
 //BA.debugLineNum = 492;BA.debugLine="btnPointInSpace.Enabled = Starter.AnchorsDefined";
mostCurrent._btnpointinspace.setEnabled(mostCurrent._starter._anchorsdefined);
 }else {
 //BA.debugLineNum = 494;BA.debugLine="btnPointInSpace.Enabled = False";
mostCurrent._btnpointinspace.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 497;BA.debugLine="Return True 'Don't try to navigate to this URL";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 499;BA.debugLine="End Sub";
return false;
}
}
