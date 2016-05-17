package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class sqlutils {
private static sqlutils mostCurrent = new sqlutils();
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
public b4a.example.functions _functions = null;
public b4a.example.dbutils _dbutils = null;
public b4a.example.elite _elite = null;
public b4a.example.commodupdate _commodupdate = null;
public b4a.example.edtables _edtables = null;
public b4a.example.anchordefine _anchordefine = null;
public static String  _createindex(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _indexname,String _fields,boolean _unique) throws Exception{
String _query = "";
String _uni = "";
 //BA.debugLineNum = 153;BA.debugLine="Sub CreateIndex(SQL As SQL, TableName As String, I";
 //BA.debugLineNum = 154;BA.debugLine="Dim query, uni As String";
_query = "";
_uni = "";
 //BA.debugLineNum = 155;BA.debugLine="uni = \"\"";
_uni = "";
 //BA.debugLineNum = 156;BA.debugLine="If Unique=True Then uni = \"UNIQUE\"";
if (_unique==anywheresoftware.b4a.keywords.Common.True) { 
_uni = "UNIQUE";};
 //BA.debugLineNum = 157;BA.debugLine="query = \"CREATE \" & uni & \" INDEX IF NOT EXISTS";
_query = "CREATE "+_uni+" INDEX IF NOT EXISTS "+_indexname+" ON ["+_tablename+"] ("+_fields+")";
 //BA.debugLineNum = 158;BA.debugLine="Log(\"CreateIndex: \" & query)";
anywheresoftware.b4a.keywords.Common.Log("CreateIndex: "+_query);
 //BA.debugLineNum = 159;BA.debugLine="SQL.ExecNonQuery(query)";
_sql.ExecNonQuery(_query);
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _dropindex(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _indexname) throws Exception{
String _query = "";
 //BA.debugLineNum = 162;BA.debugLine="Sub DropIndex(SQL As SQL, IndexName As String) ' D";
 //BA.debugLineNum = 163;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 164;BA.debugLine="query = \"DROP INDEX IF EXISTS \" & IndexName";
_query = "DROP INDEX IF EXISTS "+_indexname;
 //BA.debugLineNum = 165;BA.debugLine="Log(\"DropIndex: \" & query)";
anywheresoftware.b4a.keywords.Common.Log("DropIndex: "+_query);
 //BA.debugLineNum = 166;BA.debugLine="SQL.ExecNonQuery(query)";
_sql.ExecNonQuery(_query);
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _table_addcolumns(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fieldsandtypes) throws Exception{
int _i = 0;
String _sb = "";
String _field = "";
String _ftype = "";
boolean _fexist = false;
 //BA.debugLineNum = 22;BA.debugLine="Sub Table_AddColumns(SQL As SQL, TableName As Stri";
 //BA.debugLineNum = 23;BA.debugLine="For i = 0 To FieldsAndTypes.Size - 1";
{
final int step1 = 1;
final int limit1 = (int) (_fieldsandtypes.getSize()-1);
for (_i = (int) (0) ; (step1 > 0 && _i <= limit1) || (step1 < 0 && _i >= limit1); _i = ((int)(0 + _i + step1)) ) {
 //BA.debugLineNum = 24;BA.debugLine="Dim sb, field, ftype As String";
_sb = "";
_field = "";
_ftype = "";
 //BA.debugLineNum = 25;BA.debugLine="field = FieldsAndTypes.GetKeyAt(i)";
_field = BA.ObjectToString(_fieldsandtypes.GetKeyAt(_i));
 //BA.debugLineNum = 26;BA.debugLine="ftype = FieldsAndTypes.GetValueAt(i)";
_ftype = BA.ObjectToString(_fieldsandtypes.GetValueAt(_i));
 //BA.debugLineNum = 27;BA.debugLine="sb = \"ALTER TABLE [\" & TableName & \"] ADD COLUMN";
_sb = "ALTER TABLE ["+_tablename+"] ADD COLUMN ["+_field+"] "+_ftype;
 //BA.debugLineNum = 28;BA.debugLine="Dim fExist As Boolean = Table_FieldExists(SQL,";
_fexist = _table_fieldexists(_ba,_sql,_tablename,_field);
 //BA.debugLineNum = 29;BA.debugLine="If fExist = False Then SQL.ExecNonQuery(sb)";
if (_fexist==anywheresoftware.b4a.keywords.Common.False) { 
_sql.ExecNonQuery(_sb);};
 }
};
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static int  _table_countrows(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename) throws Exception{
int _rowtotal = 0;
 //BA.debugLineNum = 33;BA.debugLine="Sub Table_CountRows(SQL As SQL, TableName As Strin";
 //BA.debugLineNum = 34;BA.debugLine="Dim RowTotal As Int";
_rowtotal = 0;
 //BA.debugLineNum = 35;BA.debugLine="RowTotal = SQL.ExecQuerySingleResult(\"SELECT coun";
_rowtotal = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT count(*) FROM ["+_tablename+"]")));
 //BA.debugLineNum = 36;BA.debugLine="Return RowTotal";
if (true) return _rowtotal;
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return 0;
}
public static boolean  _table_fieldexists(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _fieldname) throws Exception{
anywheresoftware.b4a.objects.collections.List _fieldlist = null;
int _i = 0;
 //BA.debugLineNum = 39;BA.debugLine="Sub Table_FieldExists(SQL As SQL, TableName As Str";
 //BA.debugLineNum = 40;BA.debugLine="Dim FieldList As List";
_fieldlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 41;BA.debugLine="FieldList = Table_FieldNames(SQL, TableName)";
_fieldlist = _table_fieldnames(_ba,_sql,_tablename);
 //BA.debugLineNum = 42;BA.debugLine="For i = 0 To FieldList.Size - 1";
{
final int step3 = 1;
final int limit3 = (int) (_fieldlist.getSize()-1);
for (_i = (int) (0) ; (step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3); _i = ((int)(0 + _i + step3)) ) {
 //BA.debugLineNum = 43;BA.debugLine="If FieldName.ToLowerCase = FieldList.Get(i) Then";
if ((_fieldname.toLowerCase()).equals(BA.ObjectToString(_fieldlist.Get(_i)))) { 
 //BA.debugLineNum = 44;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 47;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return false;
}
public static anywheresoftware.b4a.objects.collections.List  _table_fieldnames(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename) throws Exception{
anywheresoftware.b4a.objects.collections.List _res1 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
int _i = 0;
 //BA.debugLineNum = 50;BA.debugLine="Sub Table_FieldNames(SQL As SQL, TableName As Stri";
 //BA.debugLineNum = 51;BA.debugLine="Dim res1 As List";
_res1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 52;BA.debugLine="res1.Initialize";
_res1.Initialize();
 //BA.debugLineNum = 53;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 54;BA.debugLine="cur = SQL.ExecQuery(\"PRAGMA table_info ([\" & Tabl";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery("PRAGMA table_info (["+_tablename+"])")));
 //BA.debugLineNum = 55;BA.debugLine="If cur.RowCount = 0 Then";
if (_cur.getRowCount()==0) { 
 //BA.debugLineNum = 56;BA.debugLine="Log(\"No records found.\")";
anywheresoftware.b4a.keywords.Common.Log("No records found.");
 //BA.debugLineNum = 57;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null));
 };
 //BA.debugLineNum = 59;BA.debugLine="For i = 0 To cur.RowCount - 1";
{
final int step9 = 1;
final int limit9 = (int) (_cur.getRowCount()-1);
for (_i = (int) (0) ; (step9 > 0 && _i <= limit9) || (step9 < 0 && _i >= limit9); _i = ((int)(0 + _i + step9)) ) {
 //BA.debugLineNum = 60;BA.debugLine="cur.Position = i";
_cur.setPosition(_i);
 //BA.debugLineNum = 61;BA.debugLine="res1.Add(cur.GetString(\"name\").ToLowerCase)";
_res1.Add((Object)(_cur.GetString("name").toLowerCase()));
 }
};
 //BA.debugLineNum = 63;BA.debugLine="cur.close";
_cur.Close();
 //BA.debugLineNum = 64;BA.debugLine="Return res1";
if (true) return _res1;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.Map  _table_information(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename) throws Exception{
anywheresoftware.b4a.objects.collections.Map _res1 = null;
anywheresoftware.b4a.objects.collections.Map _res = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
int _i = 0;
 //BA.debugLineNum = 67;BA.debugLine="Sub Table_Information(SQL As SQL, TableName As Str";
 //BA.debugLineNum = 68;BA.debugLine="Dim res1 As Map";
_res1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 69;BA.debugLine="Dim res As Map";
_res = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 70;BA.debugLine="res1.Initialize";
_res1.Initialize();
 //BA.debugLineNum = 71;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 72;BA.debugLine="cur = SQL.ExecQuery(\"PRAGMA table_info ([\" & Tabl";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery("PRAGMA table_info (["+_tablename+"])")));
 //BA.debugLineNum = 73;BA.debugLine="If cur.RowCount = 0 Then";
if (_cur.getRowCount()==0) { 
 //BA.debugLineNum = 74;BA.debugLine="Log(\"No records found.\")";
anywheresoftware.b4a.keywords.Common.Log("No records found.");
 //BA.debugLineNum = 75;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(anywheresoftware.b4a.keywords.Common.Null));
 };
 //BA.debugLineNum = 77;BA.debugLine="For i = 0 To cur.RowCount - 1";
{
final int step10 = 1;
final int limit10 = (int) (_cur.getRowCount()-1);
for (_i = (int) (0) ; (step10 > 0 && _i <= limit10) || (step10 < 0 && _i >= limit10); _i = ((int)(0 + _i + step10)) ) {
 //BA.debugLineNum = 78;BA.debugLine="cur.Position = i";
_cur.setPosition(_i);
 //BA.debugLineNum = 79;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 80;BA.debugLine="res.put(\"ID\", cur.GetString(\"cid\"))";
_res.Put((Object)("ID"),(Object)(_cur.GetString("cid")));
 //BA.debugLineNum = 81;BA.debugLine="res.put(\"Name\", cur.GetString(\"name\"))";
_res.Put((Object)("Name"),(Object)(_cur.GetString("name")));
 //BA.debugLineNum = 82;BA.debugLine="res.Put(\"Type\", cur.GetString(\"type\"))";
_res.Put((Object)("Type"),(Object)(_cur.GetString("type")));
 //BA.debugLineNum = 83;BA.debugLine="res.Put(\"Default value\", cur.GetString(\"dflt_val";
_res.Put((Object)("Default value"),(Object)(_cur.GetString("dflt_value")));
 //BA.debugLineNum = 84;BA.debugLine="res.Put(\"Not null\", cur.GetString(\"notnull\"))";
_res.Put((Object)("Not null"),(Object)(_cur.GetString("notnull")));
 //BA.debugLineNum = 85;BA.debugLine="res.Put(\"Primary key\", cur.GetString(\"pk\"))";
_res.Put((Object)("Primary key"),(Object)(_cur.GetString("pk")));
 //BA.debugLineNum = 86;BA.debugLine="res1.Put(i, res)";
_res1.Put((Object)(_i),(Object)(_res.getObject()));
 }
};
 //BA.debugLineNum = 88;BA.debugLine="cur.close";
_cur.Close();
 //BA.debugLineNum = 89;BA.debugLine="Return res1";
if (true) return _res1;
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return null;
}
public static String  _table_insertmap(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _m) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _columns = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _values = null;
anywheresoftware.b4a.objects.collections.List _listofvalues = null;
int _i2 = 0;
String _col = "";
Object _value = null;
 //BA.debugLineNum = 92;BA.debugLine="Sub Table_InsertMap(SQL As SQL, TableName As Strin";
 //BA.debugLineNum = 93;BA.debugLine="Dim sb, columns, values As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_columns = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_values = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 94;BA.debugLine="SQL.BeginTransaction";
_sql.BeginTransaction();
 //BA.debugLineNum = 95;BA.debugLine="Try";
try { //BA.debugLineNum = 96;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 97;BA.debugLine="columns.Initialize";
_columns.Initialize();
 //BA.debugLineNum = 98;BA.debugLine="values.Initialize";
_values.Initialize();
 //BA.debugLineNum = 99;BA.debugLine="Dim listOfValues As List";
_listofvalues = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 100;BA.debugLine="listOfValues.Initialize";
_listofvalues.Initialize();
 //BA.debugLineNum = 101;BA.debugLine="sb.Append(\"INSERT INTO [\" & TableName & \"] (\")";
_sb.Append("INSERT INTO ["+_tablename+"] (");
 //BA.debugLineNum = 102;BA.debugLine="For i2 = 0 To m.Size - 1";
{
final int step10 = 1;
final int limit10 = (int) (_m.getSize()-1);
for (_i2 = (int) (0) ; (step10 > 0 && _i2 <= limit10) || (step10 < 0 && _i2 >= limit10); _i2 = ((int)(0 + _i2 + step10)) ) {
 //BA.debugLineNum = 103;BA.debugLine="Dim col As String";
_col = "";
 //BA.debugLineNum = 104;BA.debugLine="Dim value As Object";
_value = new Object();
 //BA.debugLineNum = 105;BA.debugLine="col = m.GetKeyAt(i2)";
_col = BA.ObjectToString(_m.GetKeyAt(_i2));
 //BA.debugLineNum = 106;BA.debugLine="value = m.GetValueAt(i2)";
_value = _m.GetValueAt(_i2);
 //BA.debugLineNum = 107;BA.debugLine="If i2 > 0 Then";
if (_i2>0) { 
 //BA.debugLineNum = 108;BA.debugLine="columns.Append(\", \")";
_columns.Append(", ");
 //BA.debugLineNum = 109;BA.debugLine="values.Append(\", \")";
_values.Append(", ");
 };
 //BA.debugLineNum = 111;BA.debugLine="columns.Append(\"[\").Append(col).Append(\"]\")";
_columns.Append("[").Append(_col).Append("]");
 //BA.debugLineNum = 112;BA.debugLine="values.Append(\"?\")";
_values.Append("?");
 //BA.debugLineNum = 113;BA.debugLine="listOfValues.Add(value)";
_listofvalues.Add(_value);
 }
};
 //BA.debugLineNum = 115;BA.debugLine="sb.Append(columns.ToString).Append(\") VALUES (\")";
_sb.Append(_columns.ToString()).Append(") VALUES (").Append(_values.ToString()).Append(")");
 //BA.debugLineNum = 116;BA.debugLine="Log(\"InsertMap : \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.Log("InsertMap : "+_sb.ToString());
 //BA.debugLineNum = 117;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, listOfValues)";
_sql.ExecNonQuery2(_sb.ToString(),_listofvalues);
 //BA.debugLineNum = 118;BA.debugLine="SQL.TransactionSuccessful";
_sql.TransactionSuccessful();
 } 
       catch (Exception e28) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e28); //BA.debugLineNum = 120;BA.debugLine="ToastMessageShow(LastException.Message, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 121;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)));
 };
 //BA.debugLineNum = 123;BA.debugLine="SQL.EndTransaction";
_sql.EndTransaction();
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _table_listoffield(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _keyfield) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _c = null;
anywheresoftware.b4a.objects.collections.List _res = null;
String _strv = "";
int _row = 0;
 //BA.debugLineNum = 126;BA.debugLine="Public Sub Table_ListOfField(SQL As SQL, TableName";
 //BA.debugLineNum = 127;BA.debugLine="Dim c As Cursor";
_c = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 128;BA.debugLine="TableName = TableName.ToLowerCase";
_tablename = _tablename.toLowerCase();
 //BA.debugLineNum = 129;BA.debugLine="If TableName.StartsWith(\"select\") = True Then";
if (_tablename.startsWith("select")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 130;BA.debugLine="c = SQL.ExecQuery(TableName)";
_c.setObject((android.database.Cursor)(_sql.ExecQuery(_tablename)));
 }else {
 //BA.debugLineNum = 132;BA.debugLine="c = SQL.ExecQuery(\"SELECT [\" & KeyField & \"] FRO";
_c.setObject((android.database.Cursor)(_sql.ExecQuery("SELECT ["+_keyfield+"] FROM ["+_tablename+"]")));
 };
 //BA.debugLineNum = 134;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 135;BA.debugLine="Dim strV As String";
_strv = "";
 //BA.debugLineNum = 136;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 137;BA.debugLine="For row = 0 To c.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_c.getRowCount()-1);
for (_row = (int) (0) ; (step11 > 0 && _row <= limit11) || (step11 < 0 && _row >= limit11); _row = ((int)(0 + _row + step11)) ) {
 //BA.debugLineNum = 138;BA.debugLine="c.Position = row";
_c.setPosition(_row);
 //BA.debugLineNum = 139;BA.debugLine="strV = c.GetString2(0).Trim";
_strv = _c.GetString2((int) (0)).trim();
 //BA.debugLineNum = 140;BA.debugLine="If strV.Length > 0 Then res.Add(strV)";
if (_strv.length()>0) { 
_res.Add((Object)(_strv));};
 }
};
 //BA.debugLineNum = 142;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return null;
}
public static boolean  _table_recordexists(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _fldname,String _fldvalue) throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Public Sub Table_RecordExists(SQL As SQL, TableNam";
 //BA.debugLineNum = 146;BA.debugLine="Return SQL.ExecQuerySingleResult2(\"SELECT count(\"";
if (true) return (double)(Double.parseDouble(_sql.ExecQuerySingleResult2("SELECT count("+_fldname+") FROM ["+_tablename+"] WHERE ["+_fldname+"] = ?",new String[]{_fldvalue})))>0;
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return false;
}
public static String  _table_rename(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _oldname,String _newname) throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub Table_Rename(SQL As SQL, OldName As String, Ne";
 //BA.debugLineNum = 150;BA.debugLine="SQL.ExecNonQuery(\"ALTER TABLE [\" & OldName & \"] R";
_sql.ExecNonQuery("ALTER TABLE ["+_oldname+"] RENAME TO ["+_newname+"]");
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static boolean  _tableexists(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _targettbl) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rescurs = null;
boolean _ans = false;
String _exstmnt = "";
 //BA.debugLineNum = 9;BA.debugLine="Sub TableExists(SQL As SQL, targetTbl As String) A";
 //BA.debugLineNum = 10;BA.debugLine="Dim resCurs As Cursor";
_rescurs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Dim ans As Boolean";
_ans = false;
 //BA.debugLineNum = 12;BA.debugLine="Dim ExStmnt As String";
_exstmnt = "";
 //BA.debugLineNum = 14;BA.debugLine="ExStmnt = \"SELECT name FROM sqlite_master WH";
_exstmnt = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+_targettbl+"'";
 //BA.debugLineNum = 15;BA.debugLine="resCurs = SQL.ExecQuery(ExStmnt)";
_rescurs.setObject((android.database.Cursor)(_sql.ExecQuery(_exstmnt)));
 //BA.debugLineNum = 16;BA.debugLine="ans = (resCurs.RowCount = 1)";
_ans = (_rescurs.getRowCount()==1);
 //BA.debugLineNum = 17;BA.debugLine="resCurs.close";
_rescurs.Close();
 //BA.debugLineNum = 19;BA.debugLine="Return ans";
if (true) return _ans;
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return false;
}
}
