package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class anchordefine extends android.app.Service {
	public static class anchordefine_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, anchordefine.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static anchordefine mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return anchordefine.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.anchordefine");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.anchordefine", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            BA.LogInfo("** Service (anchordefine) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
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
                    BA.LogInfo("** Service (anchordefine) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
				}
			};
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (anchordefine) Start **");
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
        BA.LogInfo("** Service (anchordefine) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
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
public static String  _addcandidate(String _sector,String _system2add,double _distance) throws Exception{
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssector = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursoptcand = null;
double _weight = 0;
double _minweight = 0;
int _optcandcount = 0;
anywheresoftware.b4a.objects.collections.Map _record = null;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
 //BA.debugLineNum = 153;BA.debugLine="Sub AddCandidate(Sector As String, System2Add As S";
 //BA.debugLineNum = 154;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 155;BA.debugLine="Dim CursSector, Cursoptcand As Cursor";
_curssector = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursoptcand = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 156;BA.debugLine="Dim Weight, minWeight As Double";
_weight = 0;
_minweight = 0;
 //BA.debugLineNum = 157;BA.debugLine="Dim optcandCount As Int";
_optcandcount = 0;
 //BA.debugLineNum = 158;BA.debugLine="Dim record, whereclause As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 160;BA.debugLine="Q = \"SELECT * FROM ReferenceSector WHERE SectorNa";
_q = "SELECT * FROM ReferenceSector WHERE SectorName = ?";
 //BA.debugLineNum = 161;BA.debugLine="CursSector = Starter.SQLExec.ExecQuery2(Q, Array";
_curssector.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_q,new String[]{_sector})));
 //BA.debugLineNum = 162;BA.debugLine="CursSector.Position = 0";
_curssector.setPosition((int) (0));
 //BA.debugLineNum = 163;BA.debugLine="minWeight = CursSector.GetDouble(\"minWeight\")";
_minweight = _curssector.GetDouble("minWeight");
 //BA.debugLineNum = 165;BA.debugLine="Weight = SystemWeighting(System2Add, Distance)";
_weight = _systemweighting(_system2add,_distance);
 //BA.debugLineNum = 168;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 169;BA.debugLine="record.Put(\"SectorName\", Sector)";
_record.Put((Object)("SectorName"),(Object)(_sector));
 //BA.debugLineNum = 170;BA.debugLine="record.Put(\"SystemName\", System2Add)";
_record.Put((Object)("SystemName"),(Object)(_system2add));
 //BA.debugLineNum = 171;BA.debugLine="record.Put(\"Weight\", Weight)";
_record.Put((Object)("Weight"),(Object)(_weight));
 //BA.debugLineNum = 172;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"candida";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"candidateReferences",_record);
 //BA.debugLineNum = 175;BA.debugLine="Q = \"SELECT * FROM optcandidateReferences\"";
_q = "SELECT * FROM optcandidateReferences";
 //BA.debugLineNum = 176;BA.debugLine="Cursoptcand = Starter.SQLExec.ExecQuery(Q)";
_cursoptcand.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 177;BA.debugLine="optcandCount = Cursoptcand.RowCount";
_optcandcount = _cursoptcand.getRowCount();
 //BA.debugLineNum = 179;BA.debugLine="If Weight < minWeight Then";
if (_weight<_minweight) { 
 //BA.debugLineNum = 181;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 182;BA.debugLine="whereclause.Put(\"SectorName\", Sector)";
_whereclause.Put((Object)("SectorName"),(Object)(_sector));
 //BA.debugLineNum = 183;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"Reference";
mostCurrent._dbutils._updaterecord(processBA,mostCurrent._starter._sqlexec,"ReferenceSector","minWeight",(Object)(_weight),_whereclause);
 //BA.debugLineNum = 185;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 186;BA.debugLine="record.Put(\"SectorName\", Sector)";
_record.Put((Object)("SectorName"),(Object)(_sector));
 //BA.debugLineNum = 187;BA.debugLine="record.Put(\"SystemName\", System2Add)";
_record.Put((Object)("SystemName"),(Object)(_system2add));
 //BA.debugLineNum = 188;BA.debugLine="record.Put(\"Weight\", Weight)";
_record.Put((Object)("Weight"),(Object)(_weight));
 //BA.debugLineNum = 189;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"optcan";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"optcandidateReferences",_record);
 }else if(_optcandcount<10) { 
 //BA.debugLineNum = 192;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 193;BA.debugLine="record.Put(\"SectorName\", Sector)";
_record.Put((Object)("SectorName"),(Object)(_sector));
 //BA.debugLineNum = 194;BA.debugLine="record.Put(\"SystemName\", System2Add)";
_record.Put((Object)("SystemName"),(Object)(_system2add));
 //BA.debugLineNum = 195;BA.debugLine="record.Put(\"Weight\", Weight)";
_record.Put((Object)("Weight"),(Object)(_weight));
 //BA.debugLineNum = 196;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"optcan";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"optcandidateReferences",_record);
 }else if(_optcandcount<100 && _distance<1000 && _distance>100) { 
 //BA.debugLineNum = 199;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 200;BA.debugLine="record.Put(\"SectorName\", Sector)";
_record.Put((Object)("SectorName"),(Object)(_sector));
 //BA.debugLineNum = 201;BA.debugLine="record.Put(\"SystemName\", System2Add)";
_record.Put((Object)("SystemName"),(Object)(_system2add));
 //BA.debugLineNum = 202;BA.debugLine="record.Put(\"Weight\", Weight)";
_record.Put((Object)("Weight"),(Object)(_weight));
 //BA.debugLineNum = 203;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"optcan";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"optcandidateReferences",_record);
 };
 //BA.debugLineNum = 205;BA.debugLine="CursSector.Close";
_curssector.Close();
 //BA.debugLineNum = 206;BA.debugLine="Cursoptcand.Close";
_cursoptcand.Close();
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _addsystemstosectors() throws Exception{
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursestimated = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
double _estix = 0;
double _estiy = 0;
double _estiz = 0;
int _i = 0;
double _distance = 0;
double _azimuth = 0;
double _altitude = 0;
int _aznr = 0;
int _altnr = 0;
int _sections = 0;
b4a.example.math _m = null;
 //BA.debugLineNum = 103;BA.debugLine="Sub AddSystemsToSectors";
 //BA.debugLineNum = 104;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 105;BA.debugLine="Dim CursEstimated, CursSystems As Cursor";
_cursestimated = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 106;BA.debugLine="Dim EstiX, EstiY, EstiZ As Double";
_estix = 0;
_estiy = 0;
_estiz = 0;
 //BA.debugLineNum = 107;BA.debugLine="Dim I As Int";
_i = 0;
 //BA.debugLineNum = 108;BA.debugLine="Dim Distance, Azimuth, Altitude As Double";
_distance = 0;
_azimuth = 0;
_altitude = 0;
 //BA.debugLineNum = 109;BA.debugLine="Dim aznr, altnr, sections As Int";
_aznr = 0;
_altnr = 0;
_sections = 0;
 //BA.debugLineNum = 110;BA.debugLine="Dim m As Math";
_m = new b4a.example.math();
 //BA.debugLineNum = 112;BA.debugLine="sections = 6 ' was 12";
_sections = (int) (6);
 //BA.debugLineNum = 113;BA.debugLine="aznr = 0";
_aznr = (int) (0);
 //BA.debugLineNum = 114;BA.debugLine="altnr = 0";
_altnr = (int) (0);
 //BA.debugLineNum = 115;BA.debugLine="m.Initialize";
_m._initialize(processBA);
 //BA.debugLineNum = 118;BA.debugLine="Q = \"SELECT * FROM Systems WHERE SystemName = ?\"";
_q = "SELECT * FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 119;BA.debugLine="CursEstimated = Starter.SQLExec.ExecQuery2(Q, Arr";
_cursestimated.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_q,new String[]{mostCurrent._starter._currlocation})));
 //BA.debugLineNum = 120;BA.debugLine="If CursEstimated.RowCount > 0 Then";
if (_cursestimated.getRowCount()>0) { 
 //BA.debugLineNum = 121;BA.debugLine="CursEstimated.Position = 0";
_cursestimated.setPosition((int) (0));
 //BA.debugLineNum = 122;BA.debugLine="EstiX = CursEstimated.GetDouble(\"SpaceX\")";
_estix = _cursestimated.GetDouble("SpaceX");
 //BA.debugLineNum = 123;BA.debugLine="EstiY = CursEstimated.GetDouble(\"SpaceY\")";
_estiy = _cursestimated.GetDouble("SpaceY");
 //BA.debugLineNum = 124;BA.debugLine="EstiZ = CursEstimated.GetDouble(\"SpaceZ\")";
_estiz = _cursestimated.GetDouble("SpaceZ");
 }else {
 //BA.debugLineNum = 126;BA.debugLine="Starter.CurrLocation = \"Sol\"";
mostCurrent._starter._currlocation = "Sol";
 //BA.debugLineNum = 127;BA.debugLine="EstiX = 0.00";
_estix = 0.00;
 //BA.debugLineNum = 128;BA.debugLine="EstiY = 0.00";
_estiy = 0.00;
 //BA.debugLineNum = 129;BA.debugLine="EstiZ = 0.00";
_estiz = 0.00;
 };
 //BA.debugLineNum = 133;BA.debugLine="Q = \"SELECT * FROM Systems WHERE ExactLocation =";
_q = "SELECT * FROM Systems WHERE ExactLocation = 1";
 //BA.debugLineNum = 134;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery(Q)";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 135;BA.debugLine="CursSystems.Position = 0";
_curssystems.setPosition((int) (0));
 //BA.debugLineNum = 136;BA.debugLine="For I = 0 To CursSystems.RowCount - 1";
{
final int step28 = 1;
final int limit28 = (int) (_curssystems.getRowCount()-1);
for (_i = (int) (0) ; (step28 > 0 && _i <= limit28) || (step28 < 0 && _i >= limit28); _i = ((int)(0 + _i + step28)) ) {
 //BA.debugLineNum = 137;BA.debugLine="CursSystems.Position = I";
_curssystems.setPosition(_i);
 //BA.debugLineNum = 138;BA.debugLine="Distance = Elite.DistanceBetween(CursSystems.Get";
_distance = mostCurrent._elite._distancebetween(processBA,_curssystems.GetString("SystemName"),mostCurrent._starter._currlocation);
 //BA.debugLineNum = 139;BA.debugLine="Azimuth = ATan2((CursSystems.GetDouble(\"SpaceY\")";
_azimuth = anywheresoftware.b4a.keywords.Common.ATan2((_curssystems.GetDouble("SpaceY")-_estiy),(_curssystems.GetDouble("SpaceX")-_estix));
 //BA.debugLineNum = 140;BA.debugLine="Altitude = ACos((CursSystems.GetDouble(\"SpaceZ\")";
_altitude = anywheresoftware.b4a.keywords.Common.ACos((_curssystems.GetDouble("SpaceZ")-_estiz)/(double)_distance);
 //BA.debugLineNum = 142;BA.debugLine="If Distance > 0 Then";
if (_distance>0) { 
 //BA.debugLineNum = 143;BA.debugLine="aznr = Floor((Azimuth * 180 / m.PI + 180) / (36";
_aznr = (int) (anywheresoftware.b4a.keywords.Common.Floor((_azimuth*180/(double)_m._pi()+180)/(double)(360/(double)_sections)));
 //BA.debugLineNum = 144;BA.debugLine="altnr = Floor((Altitude * 180 / m.PI) / (360 /";
_altnr = (int) (anywheresoftware.b4a.keywords.Common.Floor((_altitude*180/(double)_m._pi())/(double)(360/(double)_sections)));
 //BA.debugLineNum = 146;BA.debugLine="AddCandidate(\"Sector:\" & aznr & \":\" & altnr, Cu";
_addcandidate("Sector:"+BA.NumberToString(_aznr)+":"+BA.NumberToString(_altnr),_curssystems.GetString("SystemName"),_distance);
 };
 }
};
 //BA.debugLineNum = 149;BA.debugLine="CursEstimated.Close";
_cursestimated.Close();
 //BA.debugLineNum = 150;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static double  _calculateangulardistance(double _long1rad,double _lati1rad,double _long2rad,double _lati2rad) throws Exception{
double _longitudediff = 0;
double _anglecalculation = 0;
b4a.example.math _m = null;
 //BA.debugLineNum = 317;BA.debugLine="Sub CalculateAngularDistance(Long1Rad As Double, L";
 //BA.debugLineNum = 318;BA.debugLine="Dim LongitudeDiff, AngleCalculation As Double";
_longitudediff = 0;
_anglecalculation = 0;
 //BA.debugLineNum = 319;BA.debugLine="Dim m As Math";
_m = new b4a.example.math();
 //BA.debugLineNum = 320;BA.debugLine="m.Initialize";
_m._initialize(processBA);
 //BA.debugLineNum = 322;BA.debugLine="LongitudeDiff = Abs(Long1Rad - Long2Rad)";
_longitudediff = anywheresoftware.b4a.keywords.Common.Abs(_long1rad-_long2rad);
 //BA.debugLineNum = 324;BA.debugLine="If LongitudeDiff > m.PI Then";
if (_longitudediff>_m._pi()) { 
 //BA.debugLineNum = 325;BA.debugLine="LongitudeDiff = 2.0 * m.PI - LongitudeDiff";
_longitudediff = 2.0*_m._pi()-_longitudediff;
 };
 //BA.debugLineNum = 328;BA.debugLine="AngleCalculation = ACos(Sin(Lati1Rad) * Sin(Lati2";
_anglecalculation = anywheresoftware.b4a.keywords.Common.ACos(anywheresoftware.b4a.keywords.Common.Sin(_lati1rad)*anywheresoftware.b4a.keywords.Common.Sin(_lati2rad)+anywheresoftware.b4a.keywords.Common.Cos(_lati1rad)*anywheresoftware.b4a.keywords.Common.Cos(_lati2rad)*anywheresoftware.b4a.keywords.Common.Cos(_longitudediff));
 //BA.debugLineNum = 330;BA.debugLine="Return AngleCalculation";
if (true) return _anglecalculation;
 //BA.debugLineNum = 332;BA.debugLine="End Sub";
return 0;
}
public static String  _createsectors() throws Exception{
anywheresoftware.b4a.objects.collections.Map _record = null;
b4a.example.math _m = null;
int _az = 0;
int _aznr = 0;
int _alt = 0;
int _altnr = 0;
int _sections = 0;
 //BA.debugLineNum = 60;BA.debugLine="Sub CreateSectors";
 //BA.debugLineNum = 61;BA.debugLine="Dim record As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 64;BA.debugLine="Dim m As Math";
_m = new b4a.example.math();
 //BA.debugLineNum = 65;BA.debugLine="Dim az, aznr, alt, altnr, sections As Int";
_az = 0;
_aznr = 0;
_alt = 0;
_altnr = 0;
_sections = 0;
 //BA.debugLineNum = 66;BA.debugLine="Dim record As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 67;BA.debugLine="sections = 12";
_sections = (int) (12);
 //BA.debugLineNum = 68;BA.debugLine="az = -180";
_az = (int) (-180);
 //BA.debugLineNum = 69;BA.debugLine="aznr = 0";
_aznr = (int) (0);
 //BA.debugLineNum = 70;BA.debugLine="alt = -90";
_alt = (int) (-90);
 //BA.debugLineNum = 71;BA.debugLine="altnr = 0";
_altnr = (int) (0);
 //BA.debugLineNum = 72;BA.debugLine="m.Initialize";
_m._initialize(processBA);
 //BA.debugLineNum = 74;BA.debugLine="Do While az < 180";
while (_az<180) {
 //BA.debugLineNum = 75;BA.debugLine="Do While alt < 90";
while (_alt<90) {
 //BA.debugLineNum = 76;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 78;BA.debugLine="record.Put(\"Sectorname\", \"Sector:\" & aznr & \":";
_record.Put((Object)("Sectorname"),(Object)("Sector:"+BA.NumberToString(_aznr)+":"+BA.NumberToString(_altnr)));
 //BA.debugLineNum = 79;BA.debugLine="record.Put(\"AzimuthStartRad\", az * m.PI / 180)";
_record.Put((Object)("AzimuthStartRad"),(Object)(_az*_m._pi()/(double)180));
 //BA.debugLineNum = 80;BA.debugLine="record.Put(\"LatitudeStartRad\", alt * m.PI / 18";
_record.Put((Object)("LatitudeStartRad"),(Object)(_alt*_m._pi()/(double)180));
 //BA.debugLineNum = 81;BA.debugLine="record.Put(\"WidthAngle\", 360 / sections)";
_record.Put((Object)("WidthAngle"),(Object)(360/(double)_sections));
 //BA.debugLineNum = 82;BA.debugLine="record.Put(\"Azimuth\", az)";
_record.Put((Object)("Azimuth"),(Object)(_az));
 //BA.debugLineNum = 83;BA.debugLine="record.Put(\"AzimuthCenter\", az + (360 / sectio";
_record.Put((Object)("AzimuthCenter"),(Object)(_az+(360/(double)_sections)/(double)2));
 //BA.debugLineNum = 84;BA.debugLine="record.Put(\"AzimuthCenterRad\", (az + (360 / se";
_record.Put((Object)("AzimuthCenterRad"),(Object)((_az+(360/(double)_sections)/(double)2)*_m._pi()/(double)180));
 //BA.debugLineNum = 85;BA.debugLine="record.Put(\"Latitude\", alt)";
_record.Put((Object)("Latitude"),(Object)(_alt));
 //BA.debugLineNum = 86;BA.debugLine="record.Put(\"LatitudeCenter\", alt + (360 / sect";
_record.Put((Object)("LatitudeCenter"),(Object)(_alt+(360/(double)_sections)/(double)2));
 //BA.debugLineNum = 87;BA.debugLine="record.Put(\"LatitudeCenterRad\", (alt + (360 /";
_record.Put((Object)("LatitudeCenterRad"),(Object)((_alt+(360/(double)_sections)/(double)2)*_m._pi()/(double)180));
 //BA.debugLineNum = 88;BA.debugLine="record.Put(\"Width\", (360 / sections) * m.PI /";
_record.Put((Object)("Width"),(Object)((360/(double)_sections)*_m._pi()/(double)180));
 //BA.debugLineNum = 89;BA.debugLine="record.Put(\"minWeight\", m.DoubleMaxValue)";
_record.Put((Object)("minWeight"),(Object)(_m._doublemaxvalue()));
 //BA.debugLineNum = 90;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec,\"Refe";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"ReferenceSector",_record);
 //BA.debugLineNum = 92;BA.debugLine="alt = alt + (360 / sections)";
_alt = (int) (_alt+(360/(double)_sections));
 //BA.debugLineNum = 93;BA.debugLine="altnr = altnr + 1";
_altnr = (int) (_altnr+1);
 }
;
 //BA.debugLineNum = 95;BA.debugLine="alt = -90 'Reset inner loop value";
_alt = (int) (-90);
 //BA.debugLineNum = 96;BA.debugLine="altnr = 0 'Reset inner loop counter";
_altnr = (int) (0);
 //BA.debugLineNum = 98;BA.debugLine="az = az + (360 / sections)";
_az = (int) (_az+(360/(double)_sections));
 //BA.debugLineNum = 99;BA.debugLine="aznr = aznr + 1";
_aznr = (int) (_aznr+1);
 }
;
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _getbestcandidate(String _sector) throws Exception{
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursoptcand = null;
anywheresoftware.b4a.objects.collections.Map _record = null;
 //BA.debugLineNum = 292;BA.debugLine="Sub GetBestCandidate(sector As String)";
 //BA.debugLineNum = 293;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 294;BA.debugLine="Dim CursOptCand As Cursor";
_cursoptcand = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 295;BA.debugLine="Dim record As Map";
_record = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 296;BA.debugLine="Q = \"SELECT * FROM optcandidateReferences WHERE S";
_q = "SELECT * FROM optcandidateReferences WHERE SectorName = ? ORDER BY Weight ASC";
 //BA.debugLineNum = 297;BA.debugLine="CursOptCand = Starter.SQLExec.ExecQuery2(Q, Array";
_cursoptcand.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_q,new String[]{_sector})));
 //BA.debugLineNum = 298;BA.debugLine="If CursOptCand.RowCount > 0 Then";
if (_cursoptcand.getRowCount()>0) { 
 //BA.debugLineNum = 299;BA.debugLine="CursOptCand.Position = 0";
_cursoptcand.setPosition((int) (0));
 //BA.debugLineNum = 302;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 303;BA.debugLine="record.Put(\"SectorName\", CursOptCand.GetString(\"";
_record.Put((Object)("SectorName"),(Object)(_cursoptcand.GetString("SectorName")));
 //BA.debugLineNum = 304;BA.debugLine="record.Put(\"SystemName\", CursOptCand.GetString(\"";
_record.Put((Object)("SystemName"),(Object)(_cursoptcand.GetString("SystemName")));
 //BA.debugLineNum = 305;BA.debugLine="record.Put(\"Weight\", CursOptCand.GetString(\"Weig";
_record.Put((Object)("Weight"),(Object)(_cursoptcand.GetString("Weight")));
 //BA.debugLineNum = 306;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec, \"usedR";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"usedReferences",_record);
 //BA.debugLineNum = 308;BA.debugLine="record.Initialize";
_record.Initialize();
 //BA.debugLineNum = 309;BA.debugLine="record.Put(\"AnchorName\", CursOptCand.GetString(\"";
_record.Put((Object)("AnchorName"),(Object)(_cursoptcand.GetString("SystemName")));
 //BA.debugLineNum = 310;BA.debugLine="record.Put(\"Weight\", CursOptCand.GetString(\"Weig";
_record.Put((Object)("Weight"),(Object)(_cursoptcand.GetString("Weight")));
 //BA.debugLineNum = 311;BA.debugLine="SQLUtils.Table_InsertMap(Starter.SQLExec, \"Ancho";
mostCurrent._sqlutils._table_insertmap(processBA,mostCurrent._starter._sqlexec,"AnchorSystems",_record);
 };
 //BA.debugLineNum = 314;BA.debugLine="CursOptCand.Close";
_cursoptcand.Close();
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public static String  _getcandidate() throws Exception{
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssectors = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursrefer = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curscandi = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssectorloop = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curscandiloop = null;
int _i = 0;
int _j = 0;
double _dist = 0;
double _mindist = 0;
double _maxdist = 0;
String _sectorcandidate = "";
 //BA.debugLineNum = 228;BA.debugLine="Sub GetCandidate";
 //BA.debugLineNum = 229;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 230;BA.debugLine="Dim CursSectors, CursRefer, CursCandi, CursSector";
_curssectors = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_cursrefer = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_curscandi = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_curssectorloop = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_curscandiloop = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 231;BA.debugLine="Dim i, j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 232;BA.debugLine="Dim Dist, minDist, maxDist As Double";
_dist = 0;
_mindist = 0;
_maxdist = 0;
 //BA.debugLineNum = 233;BA.debugLine="Dim sectorcandidate As String";
_sectorcandidate = "";
 //BA.debugLineNum = 235;BA.debugLine="maxDist = 0";
_maxdist = 0;
 //BA.debugLineNum = 236;BA.debugLine="Q = \"SELECT * FROM ReferenceSector\"";
_q = "SELECT * FROM ReferenceSector";
 //BA.debugLineNum = 237;BA.debugLine="CursSectorLoop = Starter.SQLExec.ExecQuery(Q)";
_curssectorloop.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 238;BA.debugLine="CursSectors = Starter.SQLExec.ExecQuery(Q)";
_curssectors.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 239;BA.debugLine="CursSectors.Position = 0";
_curssectors.setPosition((int) (0));
 //BA.debugLineNum = 241;BA.debugLine="For i = 0 To CursSectors.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_curssectors.getRowCount()-1);
for (_i = (int) (0) ; (step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11); _i = ((int)(0 + _i + step11)) ) {
 //BA.debugLineNum = 242;BA.debugLine="CursSectors.Position = i";
_curssectors.setPosition(_i);
 //BA.debugLineNum = 244;BA.debugLine="Q = \"SELECT * FROM usedReferences WHERE SectorNa";
_q = "SELECT * FROM usedReferences WHERE SectorName = '"+_curssectors.GetString("SectorName")+"'";
 //BA.debugLineNum = 245;BA.debugLine="CursRefer = Starter.SQLExec.ExecQuery(Q)";
_cursrefer.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 247;BA.debugLine="Q = \"SELECT * FROM candidateReferences WHERE Sec";
_q = "SELECT * FROM candidateReferences WHERE SectorName = '"+_curssectors.GetString("SectorName")+"'";
 //BA.debugLineNum = 248;BA.debugLine="CursCandi = Starter.SQLExec.ExecQuery(Q)";
_curscandi.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 250;BA.debugLine="If CursRefer.RowCount = 0 And CursCandi.RowCount";
if (_cursrefer.getRowCount()==0 && _curscandi.getRowCount()>0) { 
 //BA.debugLineNum = 251;BA.debugLine="Dist = 0";
_dist = 0;
 //BA.debugLineNum = 252;BA.debugLine="minDist = 10";
_mindist = 10;
 //BA.debugLineNum = 254;BA.debugLine="For j = 0 To CursSectorLoop.RowCount - 1";
{
final int step20 = 1;
final int limit20 = (int) (_curssectorloop.getRowCount()-1);
for (_j = (int) (0) ; (step20 > 0 && _j <= limit20) || (step20 < 0 && _j >= limit20); _j = ((int)(0 + _j + step20)) ) {
 //BA.debugLineNum = 255;BA.debugLine="CursSectorLoop.Position = j";
_curssectorloop.setPosition(_j);
 //BA.debugLineNum = 257;BA.debugLine="Q = \"SELECT * FROM candidateReferences WHERE S";
_q = "SELECT * FROM candidateReferences WHERE SectorName = '"+_curssectorloop.GetString("SectorName")+"'";
 //BA.debugLineNum = 258;BA.debugLine="CursCandiLoop = Starter.SQLExec.ExecQuery(Q)";
_curscandiloop.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 260;BA.debugLine="If CursCandi.RowCount > 0 Then";
if (_curscandi.getRowCount()>0) { 
 //BA.debugLineNum = 262;BA.debugLine="Dist = CalculateAngularDistance(CursSectors.G";
_dist = _calculateangulardistance(_curssectors.GetDouble("AzimuthCenterRad"),_curssectors.GetDouble("LatitudeCenterRad"),_curssectorloop.GetDouble("AzimuthCenterRad"),_curssectorloop.GetDouble("LatitudeCenterRad"));
 //BA.debugLineNum = 264;BA.debugLine="If Dist > 0.001 Then";
if (_dist>0.001) { 
 //BA.debugLineNum = 266;BA.debugLine="If Dist < minDist Then";
if (_dist<_mindist) { 
 //BA.debugLineNum = 267;BA.debugLine="minDist = Dist";
_mindist = _dist;
 };
 };
 };
 //BA.debugLineNum = 273;BA.debugLine="CursCandiLoop.Close";
_curscandiloop.Close();
 }
};
 //BA.debugLineNum = 277;BA.debugLine="If minDist > maxDist Then";
if (_mindist>_maxdist) { 
 //BA.debugLineNum = 278;BA.debugLine="maxDist = minDist";
_maxdist = _mindist;
 //BA.debugLineNum = 279;BA.debugLine="sectorcandidate = CursSectors.GetString(\"Secto";
_sectorcandidate = _curssectors.GetString("SectorName");
 };
 };
 //BA.debugLineNum = 283;BA.debugLine="CursRefer.Close";
_cursrefer.Close();
 //BA.debugLineNum = 284;BA.debugLine="CursCandi.Close";
_curscandi.Close();
 }
};
 //BA.debugLineNum = 287;BA.debugLine="GetBestCandidate(sectorcandidate)";
_getbestcandidate(_sectorcandidate);
 //BA.debugLineNum = 288;BA.debugLine="CursSectorLoop.Close";
_curssectorloop.Close();
 //BA.debugLineNum = 289;BA.debugLine="CursSectors.Close";
_curssectors.Close();
 //BA.debugLineNum = 290;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
int _i = 0;
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursanchors = null;
 //BA.debugLineNum = 16;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 18;BA.debugLine="Starter.AnchorsDefined = False";
mostCurrent._starter._anchorsdefined = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 20;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 23;BA.debugLine="EDTables.SystemPlotTableDelete";
mostCurrent._edtables._systemplottabledelete(processBA);
 //BA.debugLineNum = 25;BA.debugLine="EDTables.TempTables";
mostCurrent._edtables._temptables(processBA);
 //BA.debugLineNum = 28;BA.debugLine="CreateSectors";
_createsectors();
 //BA.debugLineNum = 31;BA.debugLine="AddSystemsToSectors";
_addsystemstosectors();
 //BA.debugLineNum = 34;BA.debugLine="For i = 0 To 15 ' was 15";
{
final int step7 = 1;
final int limit7 = (int) (15);
for (_i = (int) (0) ; (step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7); _i = ((int)(0 + _i + step7)) ) {
 //BA.debugLineNum = 35;BA.debugLine="GetCandidate";
_getcandidate();
 }
};
 //BA.debugLineNum = 39;BA.debugLine="UpdateAnchors ' Adds the X,Y,Z to the identified";
_updateanchors();
 //BA.debugLineNum = 41;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim CursAnchors As Cursor";
_cursanchors = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Q = \"SELECT * FROM AnchorSystems\"";
_q = "SELECT * FROM AnchorSystems";
 //BA.debugLineNum = 44;BA.debugLine="CursAnchors = Starter.SQLExec.ExecQuery(Q)";
_cursanchors.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 45;BA.debugLine="If CursAnchors.RowCount < 5 Then";
if (_cursanchors.getRowCount()<5) { 
 //BA.debugLineNum = 47;BA.debugLine="Starter.AnchorsDefined = False";
mostCurrent._starter._anchorsdefined = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 50;BA.debugLine="Starter.AnchorsDefined = True";
mostCurrent._starter._anchorsdefined = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 52;BA.debugLine="Starter.AnchorsNumber = CursAnchors.RowCount";
mostCurrent._starter._anchorsnumber = _cursanchors.getRowCount();
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static double  _systemweighting(String _system2add,double _distance) throws Exception{
int _modifier = 0;
 //BA.debugLineNum = 209;BA.debugLine="Sub SystemWeighting(System2Add As String, Distance";
 //BA.debugLineNum = 210;BA.debugLine="Dim Modifier As Int";
_modifier = 0;
 //BA.debugLineNum = 212;BA.debugLine="Modifier = 0";
_modifier = (int) (0);
 //BA.debugLineNum = 214;BA.debugLine="If Regex.IsMatch(\"\\s[A-Z][A-Z].[A-Z]\\s\", System2A";
if (anywheresoftware.b4a.keywords.Common.Regex.IsMatch("\\s[A-Z][A-Z].[A-Z]\\s",_system2add)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 215;BA.debugLine="Modifier = Modifier + 20";
_modifier = (int) (_modifier+20);
 };
 //BA.debugLineNum = 217;BA.debugLine="If Distance > 20000 Then";
if (_distance>20000) { 
 //BA.debugLineNum = 218;BA.debugLine="Modifier = Modifier + 10";
_modifier = (int) (_modifier+10);
 };
 //BA.debugLineNum = 220;BA.debugLine="If Distance > 30000 Then";
if (_distance>30000) { 
 //BA.debugLineNum = 221;BA.debugLine="Modifier = Modifier + 20";
_modifier = (int) (_modifier+20);
 };
 //BA.debugLineNum = 224;BA.debugLine="Return System2Add.Length * 2 + Sqrt(Distance) / 3";
if (true) return _system2add.length()*2+anywheresoftware.b4a.keywords.Common.Sqrt(_distance)/(double)3.5+_modifier;
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return 0;
}
public static String  _updateanchors() throws Exception{
String _q = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursanchors = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _curssystems = null;
anywheresoftware.b4a.objects.collections.Map _whereclause = null;
int _i = 0;
 //BA.debugLineNum = 334;BA.debugLine="Sub UpdateAnchors";
 //BA.debugLineNum = 335;BA.debugLine="Dim Q As String";
_q = "";
 //BA.debugLineNum = 336;BA.debugLine="Dim CursAnchors, CursSystems As Cursor";
_cursanchors = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_curssystems = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 337;BA.debugLine="Dim whereclause As Map";
_whereclause = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 338;BA.debugLine="Q = \"SELECT * FROM AnchorSystems\"";
_q = "SELECT * FROM AnchorSystems";
 //BA.debugLineNum = 339;BA.debugLine="CursAnchors = Starter.SQLExec.ExecQuery(Q)";
_cursanchors.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery(_q)));
 //BA.debugLineNum = 340;BA.debugLine="CursAnchors.Position = 0";
_cursanchors.setPosition((int) (0));
 //BA.debugLineNum = 341;BA.debugLine="For i = 0 To CursAnchors.RowCount - 1";
{
final int step7 = 1;
final int limit7 = (int) (_cursanchors.getRowCount()-1);
for (_i = (int) (0) ; (step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7); _i = ((int)(0 + _i + step7)) ) {
 //BA.debugLineNum = 342;BA.debugLine="CursAnchors.Position = i";
_cursanchors.setPosition(_i);
 //BA.debugLineNum = 343;BA.debugLine="Q = \"SELECT * FROM Systems WHERE SystemName = ?\"";
_q = "SELECT * FROM Systems WHERE SystemName = ?";
 //BA.debugLineNum = 344;BA.debugLine="CursSystems = Starter.SQLExec.ExecQuery2(Q, Arra";
_curssystems.setObject((android.database.Cursor)(mostCurrent._starter._sqlexec.ExecQuery2(_q,new String[]{_cursanchors.GetString("AnchorName")})));
 //BA.debugLineNum = 345;BA.debugLine="CursSystems.Position = 0";
_curssystems.setPosition((int) (0));
 //BA.debugLineNum = 346;BA.debugLine="whereclause.Initialize";
_whereclause.Initialize();
 //BA.debugLineNum = 347;BA.debugLine="whereclause.Put(\"AnchorName\", CursSystems.GetStr";
_whereclause.Put((Object)("AnchorName"),(Object)(_curssystems.GetString("SystemName")));
 //BA.debugLineNum = 348;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"AnchorSys";
mostCurrent._dbutils._updaterecord(processBA,mostCurrent._starter._sqlexec,"AnchorSystems","SpaceX",(Object)(_curssystems.GetDouble("SpaceX")),_whereclause);
 //BA.debugLineNum = 349;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"AnchorSys";
mostCurrent._dbutils._updaterecord(processBA,mostCurrent._starter._sqlexec,"AnchorSystems","SpaceY",(Object)(_curssystems.GetDouble("SpaceY")),_whereclause);
 //BA.debugLineNum = 350;BA.debugLine="DBUtils.UpdateRecord(Starter.SQLExec, \"AnchorSys";
mostCurrent._dbutils._updaterecord(processBA,mostCurrent._starter._sqlexec,"AnchorSystems","SpaceZ",(Object)(_curssystems.GetDouble("SpaceZ")),_whereclause);
 }
};
 //BA.debugLineNum = 352;BA.debugLine="CursAnchors.Close";
_cursanchors.Close();
 //BA.debugLineNum = 353;BA.debugLine="CursSystems.Close";
_curssystems.Close();
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
}
