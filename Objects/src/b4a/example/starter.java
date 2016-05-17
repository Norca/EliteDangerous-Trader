package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends android.app.Service {
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        processBA.setActivityPaused(false);
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            BA.LogInfo("** Service (starter) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			if (ServiceHelper.StarterHelper.waitForLayout != null)
				BA.handler.post(ServiceHelper.StarterHelper.waitForLayout);
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA))
			handleStart(intent);
		else {
			ServiceHelper.StarterHelper.waitForLayout = new Runnable() {
				public void run() {
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
				}
			};
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        BA.LogInfo("** Service (starter) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _sqlexec = null;
public static boolean _anchorsdefined = false;
public static int _anchorsnumber = 0;
public static int _systemmoves = 0;
public static String _currlocation = "";
public static int _maxlydist = 0;
public static String _currstation = "";
public static int _bordercolour = 0;
public static int _textcolour = 0;
public static int _backcolour1 = 0;
public static int _backcolour2 = 0;
public static int _alphacolour1 = 0;
public static int _alphacolour2 = 0;
public static int _highlightcolour = 0;
public b4a.example.main _main = null;
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
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 85;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim SQLExec As SQL";
_sqlexec = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 10;BA.debugLine="Dim AnchorsDefined As Boolean";
_anchorsdefined = false;
 //BA.debugLineNum = 11;BA.debugLine="Dim AnchorsNumber As Int";
_anchorsnumber = 0;
 //BA.debugLineNum = 12;BA.debugLine="Dim SystemMoves As Int";
_systemmoves = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim CurrLocation As String";
_currlocation = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim MaxLYDist As Int";
_maxlydist = 0;
 //BA.debugLineNum = 16;BA.debugLine="Dim CurrStation As String";
_currstation = "";
 //BA.debugLineNum = 19;BA.debugLine="Dim BorderColour As Int";
_bordercolour = 0;
 //BA.debugLineNum = 20;BA.debugLine="Dim TextColour As Int";
_textcolour = 0;
 //BA.debugLineNum = 21;BA.debugLine="Dim BackColour1 As Int";
_backcolour1 = 0;
 //BA.debugLineNum = 22;BA.debugLine="Dim BackColour2 As Int";
_backcolour2 = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim AlphaColour1 As Int";
_alphacolour1 = 0;
 //BA.debugLineNum = 24;BA.debugLine="Dim AlphaColour2 As Int";
_alphacolour2 = 0;
 //BA.debugLineNum = 25;BA.debugLine="Dim HighlightColour As Int";
_highlightcolour = 0;
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 33;BA.debugLine="AnchorsDefined = False	' Anchor Systems are not d";
_anchorsdefined = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 34;BA.debugLine="AnchorsNumber = 0";
_anchorsnumber = (int) (0);
 //BA.debugLineNum = 35;BA.debugLine="SystemMoves = 0";
_systemmoves = (int) (0);
 //BA.debugLineNum = 36;BA.debugLine="SQLExec.Initialize(File.DirRootExternal, \"EliteTr";
_sqlexec.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 38;BA.debugLine="CurrLocation = \" \"";
_currlocation = " ";
 //BA.debugLineNum = 39;BA.debugLine="CurrStation = \"Not Set\"";
_currstation = "Not Set";
 //BA.debugLineNum = 40;BA.debugLine="MaxLYDist = 0";
_maxlydist = (int) (0);
 //BA.debugLineNum = 42;BA.debugLine="BorderColour = Colors.RGB(0,100,0) 'Dark Green";
_bordercolour = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (100),(int) (0));
 //BA.debugLineNum = 43;BA.debugLine="TextColour = Colors.RGB(255,255,255) 'White";
_textcolour = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (255),(int) (255));
 //BA.debugLineNum = 44;BA.debugLine="BackColour1 = Colors.RGB(0,100,0) 'Dark Green";
_backcolour1 = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (100),(int) (0));
 //BA.debugLineNum = 45;BA.debugLine="BackColour2 = Colors.RGB(46,139,87) 'Sea Green";
_backcolour2 = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (46),(int) (139),(int) (87));
 //BA.debugLineNum = 46;BA.debugLine="AlphaColour1 = Colors.ARGB(64,0,100,0) 'Transpare";
_alphacolour1 = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (64),(int) (0),(int) (100),(int) (0));
 //BA.debugLineNum = 47;BA.debugLine="AlphaColour2 = Colors.ARGB(64,46,139,87) 'Transpa";
_alphacolour2 = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (64),(int) (46),(int) (139),(int) (87));
 //BA.debugLineNum = 48;BA.debugLine="HighlightColour = Colors.RGB(124,252,0) 'Lawn Gre";
_highlightcolour = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (124),(int) (252),(int) (0));
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
String _q = "";
String _locname = "";
String _statname = "";
int _maxdist = 0;
 //BA.debugLineNum = 52;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 54;BA.debugLine="If File.Exists(File.DirRootExternal, \"EliteTrade.";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 55;BA.debugLine="SQLExec.Initialize(File.DirRootExternal, \"EliteT";
_sqlexec.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"EliteTrade.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 56;BA.debugLine="EDTables.DatabaseSetup";
mostCurrent._edtables._databasesetup(processBA);
 };
 //BA.debugLineNum = 59;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 61;BA.debugLine="Dim LocName As String";
_locname = "";
 //BA.debugLineNum = 62;BA.debugLine="Dim StatName As String";
_statname = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim MaxDist As Int";
_maxdist = 0;
 //BA.debugLineNum = 66;BA.debugLine="Q = \"SELECT SystemName FROM Location WHERE Locati";
_q = "SELECT SystemName FROM Location WHERE LocationID = 1";
 //BA.debugLineNum = 67;BA.debugLine="LocName = SQLExec.ExecQuerySingleResult(Q)";
_locname = _sqlexec.ExecQuerySingleResult(_q);
 //BA.debugLineNum = 68;BA.debugLine="CurrLocation = LocName";
_currlocation = _locname;
 //BA.debugLineNum = 70;BA.debugLine="Q = \"SELECT StationName FROM Location WHERE Locat";
_q = "SELECT StationName FROM Location WHERE LocationID = 1";
 //BA.debugLineNum = 71;BA.debugLine="StatName = SQLExec.ExecQuerySingleResult(Q)";
_statname = _sqlexec.ExecQuerySingleResult(_q);
 //BA.debugLineNum = 72;BA.debugLine="CurrStation = StatName";
_currstation = _statname;
 //BA.debugLineNum = 74;BA.debugLine="Q = \"SELECT TradeDistance FROM MaxLYTradeDistance";
_q = "SELECT TradeDistance FROM MaxLYTradeDistance WHERE MaxLYID = 1";
 //BA.debugLineNum = 75;BA.debugLine="MaxDist = SQLExec.ExecQuerySingleResult(Q)";
_maxdist = (int)(Double.parseDouble(_sqlexec.ExecQuerySingleResult(_q)));
 //BA.debugLineNum = 76;BA.debugLine="MaxLYDist = MaxDist";
_maxlydist = _maxdist;
 //BA.debugLineNum = 79;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
}
