Type=Activity
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim alphalistStations As List

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private btnAdd As Button
	Private btnCancel As Button
	Private btnDelete As Button
	Private btnExit As Button
	Private btnSave As Button

	Private edtStationName As EditText
	Private edtDistArrPnt As EditText
	
	Private lblTitle As Label
	
	Private spnStationType As Spinner
	
	Private tbnBlack As ToggleButton
	Private tbnEcon1 As ToggleButton
	Private tbnEcon2 As ToggleButton
	Private tbnEcon4 As ToggleButton
	Private tbnEcon8 As ToggleButton
	Private tbnEcon16 As ToggleButton
	Private tbnEcon32 As ToggleButton
	Private tbnEcon64 As ToggleButton
	Private tbnEcon128 As ToggleButton
	Private tbnEcon256 As ToggleButton
	Private tbnEcon512 As ToggleButton
	Private tbnEcon1024 As ToggleButton
	Private tbnEcon2048 As ToggleButton
	Private tbnEcon4096 As ToggleButton
	Private tbnEcon8192 As ToggleButton
	Private tbnEcon16384 As ToggleButton
	Private tbnEcon32768 As ToggleButton
	Private tbnEcon65536 As ToggleButton
	Private tbnEcon131072 As ToggleButton
	Private tbnEcon262144 As ToggleButton
	Private tbnEcon524288 As ToggleButton
	
	Private wbvStationList As WebView
	
	Dim AddOREdit As Boolean 'True equals Add, False equals Edit
	Dim result As Boolean
	
	Dim resultnum As Int
	Dim oldStatTypeDesc As String

	Dim Q As String
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("StationsMaint")

	lblTitle.Text = "Stations for " & Starter.CurrLocation & " system"
	
	InitSpinners

	Functions.SetColours(Activity)

	InitEconButtons
	
	FillStationsList
	
	ButtonReset(False)
	
	StatusReset(False)
	
	NavEdit(True)
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#Region Setup

Sub ExpandStations
	Dim i As Int, EconInt As Int
	Dim CursSystem As Cursor, CursStations As Cursor
	Dim sb As StringBuilder
	Dim record As Map
	
	Q = "DELETE FROM TempStations"
	Starter.SQLExec.ExecNonQuery(Q)
	
	Q = "SELECT SystemName FROM Systems WHERE SystemName = ?"
	CursSystem = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
	If CursSystem.RowCount > 0 Then
		CursSystem.Position = 0
		Q = "SELECT * FROM Stations WHERE SystemName = ?"
		CursStations = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
		If CursStations.RowCount > 0 Then
			For i = 0 To CursStations.RowCount - 1
				CursStations.Position = i
				sb.Initialize
				EconInt = CursStations.GetInt("EconomyNum")
				#Region EconomyBreakDown
				If EconInt > 524287 Then
					sb.Append(Elite.FindEconomy(524288))
					EconInt = EconInt - 524288
				End If
				If EconInt > 262143 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(262144))
					EconInt = EconInt - 262144
				End If
				If EconInt > 131071 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(131072))
					EconInt = EconInt - 131072
				End If
				If EconInt > 65535 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(65536))
					EconInt = EconInt - 65536
				End If
				If EconInt > 32767 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(32768))
					EconInt = EconInt - 32768
				End If
				If EconInt > 16383 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(16384))
					EconInt = EconInt - 16384
				End If
				If EconInt > 8191 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(8192))
					EconInt = EconInt - 8192
				End If
				If EconInt > 4095 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(4096))
					EconInt = EconInt - 4096
				End If
				If EconInt > 2047 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(2048))
					EconInt = EconInt - 2048
				End If
				If EconInt > 1023 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(1024))
					EconInt = EconInt - 1024
				End If
				If EconInt > 511 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(512))
					EconInt = EconInt - 512
				End If
				If EconInt > 255 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(256))
					EconInt = EconInt - 256
				End If
				If EconInt > 127 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(128))
					EconInt = EconInt - 128
				End If
				If EconInt > 63 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(64))
					EconInt = EconInt - 64
				End If
				If EconInt > 31 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(32))
					EconInt = EconInt - 32
				End If
				If EconInt > 15 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(16))
					EconInt = EconInt - 16
				End If
				If EconInt > 7 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(8))
					EconInt = EconInt - 8
				End If
				If EconInt > 3 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(4))
					EconInt = EconInt - 4
				End If
				If EconInt > 1 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(2))
					EconInt = EconInt - 2
				End If
				If EconInt > 0 Then
					If sb.Length > 0 Then
						sb.Append(", ")
					End If
					sb.Append(Elite.FindEconomy(1))
					EconInt = EconInt - 1
				End If
				#End Region
				record.Initialize
				record.Put("StationName", CursStations.GetString("StationName"))
				record.Put("SystemName", CursStations.GetString("SystemName"))
				record.Put("StatTypeDesc", CursStations.GetString("StatTypeDesc"))
				record.Put("PopulationSize", CursStations.GetString("PopulationSize"))
				record.Put("EconomyNum", CursStations.GetInt("EconomyNum"))
				record.Put("ECDesc", sb.ToString)
				record.Put("BlackMarketAvailable", CursStations.GetInt("BlackMarketAvailable"))
				record.Put("ArrivalPoint", CursStations.GetDouble("ArrivalPoint"))
				SQLUtils.Table_InsertMap(Starter.SQLExec,"TempStations",record)
			Next
		End If
		CursStations.Close
	End If
	CursSystem.Close
	
End Sub

Sub InitSpinners
	Dim i As Int
	Dim CursStationTypes As Cursor
	
	'Station Type spinner
	Q = "SELECT * FROM StationTypes ORDER BY NumOfStations DESC"
	CursStationTypes = Starter.SQLExec.ExecQuery(Q)
	If CursStationTypes.RowCount > 0 Then
		spnStationType.Clear
		spnStationType.Add("Not Set")
		For i = 0 To CursStationTypes.RowCount - 1
			CursStationTypes.Position = i
			spnStationType.Add(CursStationTypes.GetString("StatTypeDesc"))
		Next
	End If
	spnStationType.SelectedIndex = 0
	CursStationTypes.Close

End Sub

Sub InitEconButtons
	Dim i As Int
	Dim CursEconomies As Cursor

	'Economy toggle buttons
	Q = "SELECT * FROM Economies ORDER BY EconomyID ASC"
	CursEconomies = Starter.SQLExec.ExecQuery(Q)
	If CursEconomies.RowCount > 0 Then
		For i = 0 To CursEconomies.RowCount - 1
			CursEconomies.Position = i
			Select True
				Case CursEconomies.GetInt("EconomyID") = 1
					tbnEcon1.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon1.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 2
					tbnEcon2.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon2.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 4
					tbnEcon4.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon4.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 8
					tbnEcon8.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon8.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 16
					tbnEcon16.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon16.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 32
					tbnEcon32.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon32.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 64
					tbnEcon64.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon64.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 128
					tbnEcon128.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon128.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 256
					tbnEcon256.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon256.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 512
					tbnEcon512.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon512.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 1024
					tbnEcon1024.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon1024.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 2048
					tbnEcon2048.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon2048.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 4096
					tbnEcon4096.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon4096.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 8192
					tbnEcon8192.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon8192.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 16384
					tbnEcon16384.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon16384.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 32768
					tbnEcon32768.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon32768.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 65536
					tbnEcon65536.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon65536.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 131072
					tbnEcon131072.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon131072.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 262144
					tbnEcon262144.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon262144.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
				Case CursEconomies.GetInt("EconomyID") = 524288
					tbnEcon524288.TextOff = CursEconomies.GetString("EconomyDesc")
					tbnEcon524288.TextOn = CursEconomies.GetString("EconomyDesc").ToUpperCase
			End Select
		Next
	End If
	CursEconomies.Close

End Sub

Sub StationsListAlpha
	Q = "SELECT * FROM TempStations ORDER BY ArrivalPoint ASC"
	
	alphalistStations = DBUtils.ExecuteMemoryTable(Starter.SQLExec, Q, Null, 0)

End Sub

Sub FillStationsList

	ExpandStations

	Q = "SELECT TS.StationName AS [Station], TS.ECDesc AS [Economy], TS.StatTypeDesc AS [Type], CASE TS.BlackMarketAvailable WHEN 0 THEN 'False' WHEN 1 THEN 'True' END As [Black Market], TS.ArrivalPoint AS [Arrival Point] FROM TempStations TS ORDER BY TS.ArrivalPoint ASC"
	wbvStationList.LoadHtml(DBUtils.ExecuteHtml(Starter.SQLExec,Q,Null,0,True))
	
	StationsListAlpha 'Sets the ID lookup list of the systems

End Sub

#End Region

#Region Buttons

Sub btnSave_Click
	Dim counter As Int
	Dim record As Map, whereclause As Map
	If AddOREdit = True Then
		'Insert new record
		If edtStationName.Text.Length > 0 Then
			If Functions.StationExists(edtStationName.Text) = False Then
				record.Initialize
				record.Put("StationName", edtStationName.Text.ToUpperCase)
				record.Put("SystemName", Starter.CurrLocation)
				record.Put("StatTypeDesc", spnStationType.SelectedItem)
				record.Put("EconomyNum", CombineEconomy)
				record.Put("BlackMarketAvailable", tbnBlack.Checked)
				record.Put("ArrivalPoint", edtDistArrPnt.Text)
				SQLUtils.Table_InsertMap(Starter.SQLExec,"Stations",record)
				' Get current counter value of selected station type
				If spnStationType.SelectedItem <> "Not Set" Then
					Q = "SELECT NumOfStations FROM StationTypes WHERE StatTypeDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(spnStationType.SelectedItem))
					' Add one to the counter value
					counter = counter + 1
					' Set counter value of selected station type
					whereclause.Initialize
					whereclause.Put("StatTypeDesc", spnStationType.SelectedItem)
					DBUtils.UpdateRecord(Starter.SQLExec,"StationTypes","NumOfStations",counter,whereclause)
				End If
			End If
			result = True
		Else
			Msgbox("Must enter a name for the Station before save is possible", "A T T E N T I O N")
			result = False
		End If
	Else
		'Update existing record
		If edtStationName.Text.Length > 0 Then
			'If Functions.StationExists(edtStationName.Text.ToUpperCase) = False Then
				If oldStatTypeDesc <> spnStationType.SelectedItem And oldStatTypeDesc <> "Not Set" Then
					' Reduce the old Station Type counter
					Q = "SELECT NumOfStations FROM StationTypes WHERE StatTypeDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(oldStatTypeDesc))
					counter = counter - 1
					whereclause.Initialize
					whereclause.Put("StatTypeDesc", oldStatTypeDesc)
					DBUtils.UpdateRecord(Starter.SQLExec,"StationTypes","NumOfStations",counter,whereclause)
					' Increase the new Station Type counter
					Q = "SELECT NumOfStations FROM StationTypes WHERE StatTypeDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(spnStationType.SelectedItem))
					counter = counter + 1
					whereclause.Initialize
					whereclause.Put("StatTypeDesc", spnStationType.SelectedItem)
					DBUtils.UpdateRecord(Starter.SQLExec,"StationTypes","NumOfStations",counter,whereclause)
				End If
				whereclause.Initialize
				whereclause.Put("StationName", edtStationName.Text.ToUpperCase)
				record.Initialize
				record.Put("StatTypeDesc", spnStationType.SelectedItem)
				record.Put("EconomyNum", CombineEconomy)
				record.Put("BlackMarketAvailable", tbnBlack.Checked)
				record.Put("ArrivalPoint", edtDistArrPnt.Text)
				DBUtils.UpdateRecord2(Starter.SQLExec,"Stations",record,whereclause)
			'End If
			result = True
		Else
			Msgbox("Station name can not be blank", "A T T E N T I O N")
			result = False
		End If
	End If
	
	If result = True Then
		'Reset after save complete
		ButtonReset(False)
		
		StatusReset(False)
	
		NavEdit(True)
		
		FillStationsList
	End If
	
End Sub

Sub btnExit_Click
	Activity.Finish
	
End Sub

Sub btnDelete_Click
	Dim Answ As Int
	
	Answ = Msgbox2("Do you really want to delete this Station and associated market info ?", "DELETE record", "Yes", "", "No", Null)
	
	If Answ = DialogResponse.POSITIVE Then
		
		Q = "DELETE FROM StockMarket WHERE StationName = ?"
		Starter.SQLExec.ExecNonQuery2(Q, Array As String(edtStationName.Text.ToUpperCase))
		
		Dim mp As Map
		mp.Initialize
		mp.Put("StationName", edtStationName.Text.ToUpperCase)
		DBUtils.DeleteRecord(Starter.SQLExec, "Stations", mp)

		FillStationsList
	End If

	ButtonReset(False)

	StatusReset(False)
	
	NavEdit(True)

End Sub

Sub btnCancel_Click
	ButtonReset(False)

	StatusReset(False)
	
	NavEdit(True)

End Sub

Sub btnAdd_Click
	AddOREdit = True 'Add new record
	
	ButtonReset(True)
	
	NavEdit(False)
	
End Sub

#End Region

#Region Actions

Sub edtStationName_FocusChanged (HasFocus As Boolean)
	If HasFocus = False Then
		edtStationName.Text = edtStationName.Text.ToUpperCase
	End If	
End Sub

Sub spnStationType_ItemClick (Position As Int, Value As Object)
	If Value = "Settlement" Or Value = "Surface Port" Then
		' Disable the space economies
		tbnEcon1.Enabled = False
		tbnEcon4.Enabled = False
		tbnEcon16.Enabled = False
		tbnEcon64.Enabled = False
		tbnEcon256.Enabled = False
		tbnEcon1024.Enabled = False
		tbnEcon4096.Enabled = False
		tbnEcon16384.Enabled = False
		tbnEcon65536.Enabled = False
		tbnEcon262144.Enabled = False
		' Enable the surface economies
		tbnEcon2.Enabled = True
		tbnEcon8.Enabled = True
		tbnEcon32.Enabled = True
		tbnEcon128.Enabled = True
		tbnEcon512.Enabled = True
		tbnEcon2048.Enabled = True
		tbnEcon8192.Enabled = True
		tbnEcon32768.Enabled = True
		tbnEcon131072.Enabled = True
		tbnEcon524288.Enabled = True
		' Ensure space economies are reset to off
		tbnEcon1.Checked = False
		tbnEcon4.Checked = False
		tbnEcon16.Checked = False
		tbnEcon64.Checked = False
		tbnEcon256.Checked = False
		tbnEcon1024.Checked = False
		tbnEcon4096.Checked = False
		tbnEcon16384.Checked = False
		tbnEcon65536.Checked = False
		tbnEcon262144.Checked = False
	Else
		' Enable the space economies
		tbnEcon1.Enabled = True
		tbnEcon4.Enabled = True
		tbnEcon16.Enabled = True
		tbnEcon64.Enabled = True
		tbnEcon256.Enabled = True
		tbnEcon1024.Enabled = True
		tbnEcon4096.Enabled = True
		tbnEcon16384.Enabled = True
		tbnEcon65536.Enabled = True
		tbnEcon262144.Enabled = True
		' Disable the surface economies
		tbnEcon2.Enabled = False
		tbnEcon8.Enabled = False
		tbnEcon32.Enabled = False
		tbnEcon128.Enabled = False
		tbnEcon512.Enabled = False
		tbnEcon2048.Enabled = False
		tbnEcon8192.Enabled = False
		tbnEcon32768.Enabled = False
		tbnEcon131072.Enabled = False
		tbnEcon524288.Enabled = False
		' Ensure surface economies are reset to off
		tbnEcon2.Checked = False
		tbnEcon8.Checked = False
		tbnEcon32.Checked = False
		tbnEcon128.Checked = False
		tbnEcon512.Checked = False
		tbnEcon2048.Checked = False
		tbnEcon8192.Checked = False
		tbnEcon32768.Checked = False
		tbnEcon131072.Checked = False
		tbnEcon524288.Checked = False
	End If
End Sub

Sub wbvStationList_OverrideUrl (Url As String) As Boolean
	'Prase the row and column numbers from the URL
	Dim Values() As String
	Dim Row As Int
	
	Values = Regex.Split("[.]", Url.SubString(7))
	Row = Values(1)
	
	Dim Val(7) As String
	
	Val = alphalistStations.Get(Row)
	
	edtStationName.Text = Val(0).ToUpperCase
	SplitEconomy(Val(4))
	spnStationType.SelectedIndex = spnStationType.IndexOf(Val(2))
	tbnBlack.Checked = Functions.IntToBool(Val(6))
	edtDistArrPnt.Text = Val(7)
	
	oldStatTypeDesc = Val(2)	'Current Station Type Desc
	
	AddOREdit = False 'Edit existing record
	
	ButtonReset(True)
	
	NavEdit(False)

	Return True 'Don't try to navigate to this URL
	
End Sub

Sub CombineEconomy As Int
	resultnum = 0
	
	If tbnEcon524288.Checked = True Then
		resultnum = resultnum + 524288
	End If
	If tbnEcon262144.Checked = True Then
		resultnum = resultnum + 262144
	End If
	If tbnEcon131072.Checked = True Then
		resultnum = resultnum + 131072
	End If
	If tbnEcon65536.Checked = True Then
		resultnum = resultnum + 65536
	End If
	If tbnEcon32768.Checked = True Then
		resultnum = resultnum + 32768
	End If
	If tbnEcon16384.Checked = True Then
		resultnum = resultnum + 16384
	End If
	If tbnEcon8192.Checked = True Then
		resultnum = resultnum + 8192
	End If
	If tbnEcon4096.Checked = True Then
		resultnum = resultnum + 4096
	End If
	If tbnEcon2048.Checked = True Then
		resultnum = resultnum + 2048
	End If
	If tbnEcon1024.Checked = True Then
		resultnum = resultnum + 1024
	End If
	If tbnEcon512.Checked = True Then
		resultnum = resultnum + 512
	End If
	If tbnEcon256.Checked = True Then
		resultnum = resultnum + 256
	End If
	If tbnEcon128.Checked = True Then
		resultnum = resultnum + 128
	End If
	If tbnEcon64.Checked = True Then
		resultnum = resultnum + 64
	End If
	If tbnEcon32.Checked = True Then
		resultnum = resultnum + 32
	End If
	If tbnEcon16.Checked = True Then
		resultnum = resultnum + 16
	End If
	If tbnEcon8.Checked = True Then
		resultnum = resultnum + 8
	End If
	If tbnEcon4.Checked = True Then
		resultnum = resultnum + 4
	End If
	If tbnEcon2.Checked = True Then
		resultnum = resultnum + 2
	End If
	If tbnEcon1.Checked = True Then
		resultnum = resultnum + 1
	End If

	Return resultnum

End Sub

Sub SplitEconomy(EconInt As Int)
	If EconInt > 524287 Then
		tbnEcon524288.Checked = True
		EconInt = EconInt - 524288
	End If
	If EconInt > 262143 Then
		tbnEcon262144.Checked = True
		EconInt = EconInt - 262144
	End If
	If EconInt > 131071 Then
		tbnEcon131072.Checked = True
		EconInt = EconInt - 131072
	End If
	If EconInt > 65535 Then
		tbnEcon65536.Checked = True
		EconInt = EconInt - 65536
	End If
	If EconInt > 32767 Then
		tbnEcon32768.Checked = True
		EconInt = EconInt - 32768
	End If
	If EconInt > 16383 Then
		tbnEcon16384.Checked = True
		EconInt = EconInt - 16384
	End If
	If EconInt > 8191 Then
		tbnEcon8192.Checked = True
		EconInt = EconInt - 8192
	End If
	If EconInt > 4095 Then
		tbnEcon4096.Checked = True
		EconInt = EconInt - 4096
	End If
	If EconInt > 2047 Then
		tbnEcon2048.Checked = True
		EconInt = EconInt - 2048
	End If
	If EconInt > 1023 Then
		tbnEcon1024.Checked = True
		EconInt = EconInt - 1024
	End If
	If EconInt > 511 Then
		tbnEcon512.Checked = True
		EconInt = EconInt - 512
	End If
	If EconInt > 255 Then
		tbnEcon256.Checked = True
		EconInt = EconInt - 256
	End If
	If EconInt > 127 Then
		tbnEcon128.Checked = True
		EconInt = EconInt - 128
	End If
	If EconInt > 63 Then
		tbnEcon64.Checked = True
		EconInt = EconInt - 64
	End If
	If EconInt > 31 Then
		tbnEcon32.Checked = True
		EconInt = EconInt - 32
	End If
	If EconInt > 15 Then
		tbnEcon16.Checked = True
		EconInt = EconInt - 16
	End If
	If EconInt > 7 Then
		tbnEcon8.Checked = True
		EconInt = EconInt - 8
	End If
	If EconInt > 3 Then
		tbnEcon4.Checked = True
		EconInt = EconInt - 4
	End If
	If EconInt > 1 Then
		tbnEcon2.Checked = True
		EconInt = EconInt - 2
	End If
	If EconInt > 0 Then
		tbnEcon1.Checked = True
		EconInt = EconInt - 1
	End If

End Sub

Sub ButtonReset(Status As Boolean)
	If Status = True Then
		edtStationName.Enabled = True
		edtDistArrPnt.Enabled = True
		spnStationType.Enabled = True
		tbnBlack.Enabled = True
		tbnEcon1.Enabled = True
		tbnEcon2.Enabled = True
		tbnEcon4.Enabled = True
		tbnEcon8.Enabled = True
		tbnEcon16.Enabled = True
		tbnEcon32.Enabled = True
		tbnEcon64.Enabled = True
		tbnEcon128.Enabled = True
		tbnEcon256.Enabled = True
		tbnEcon512.Enabled = True
		tbnEcon1024.Enabled = True
		tbnEcon2048.Enabled = True
		tbnEcon4096.Enabled = True
		tbnEcon8192.Enabled = True
		tbnEcon16384.Enabled = True
		tbnEcon32768.Enabled = True
		tbnEcon65536.Enabled = True
		tbnEcon131072.Enabled = True
		tbnEcon262144.Enabled = True
		tbnEcon524288.Enabled = True
	Else
		edtStationName.Enabled = False
		edtDistArrPnt.Enabled = False
		spnStationType.Enabled = False
		tbnBlack.Enabled = False
		tbnEcon1.Enabled = False
		tbnEcon2.Enabled = False
		tbnEcon4.Enabled = False
		tbnEcon8.Enabled = False
		tbnEcon16.Enabled = False
		tbnEcon32.Enabled = False
		tbnEcon64.Enabled = False
		tbnEcon128.Enabled = False
		tbnEcon256.Enabled = False
		tbnEcon512.Enabled = False
		tbnEcon1024.Enabled = False
		tbnEcon2048.Enabled = False
		tbnEcon4096.Enabled = False
		tbnEcon8192.Enabled = False
		tbnEcon16384.Enabled = False
		tbnEcon32768.Enabled = False
		tbnEcon65536.Enabled = False
		tbnEcon131072.Enabled = False
		tbnEcon262144.Enabled = False
		tbnEcon524288.Enabled = False
	End If
End Sub

Sub StatusReset(Status As Boolean)
	If Status = True Then
	
	Else
		edtStationName.Text = ""
		edtDistArrPnt.Text = ""
		spnStationType.SelectedIndex = 0
		tbnBlack.Checked = False
		tbnEcon1.Checked = False
		tbnEcon2.Checked = False
		tbnEcon4.Checked = False
		tbnEcon8.Checked = False
		tbnEcon16.Checked = False
		tbnEcon32.Checked = False
		tbnEcon64.Checked = False
		tbnEcon128.Checked = False
		tbnEcon256.Checked = False
		tbnEcon512.Checked = False
		tbnEcon1024.Checked = False
		tbnEcon2048.Checked = False
		tbnEcon4096.Checked = False
		tbnEcon8192.Checked = False
		tbnEcon16384.Checked = False
		tbnEcon32768.Checked = False
		tbnEcon65536.Checked = False
		tbnEcon131072.Checked = False
		tbnEcon262144.Checked = False
		tbnEcon524288.Checked = False
	End If
End Sub

Sub NavEdit(Status As Boolean)
	If Status = True Then
		btnAdd.Visible = True
		btnCancel.Visible = False
		btnDelete.Visible = False
		btnSave.Visible = False
	Else
		btnAdd.Visible = False
		btnCancel.Visible = True
		btnDelete.Visible = True
		btnSave.Visible = True
	End If
End Sub

#End Region

