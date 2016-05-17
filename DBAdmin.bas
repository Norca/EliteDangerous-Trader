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

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnCommod As Button
	Private btnExit As Button
	
	Private tbnAlleg As ToggleButton
	Private tbnPopu As ToggleButton
	Private tbnComGrp As ToggleButton
	Private tbnCommod As ToggleButton
	Private tbnRares As ToggleButton
	Private tbnCurrLoc As ToggleButton
	Private tbnEcon As ToggleButton
	Private tbnGov As ToggleButton
	Private tbnMaxLY As ToggleButton
	Private tbnStations As ToggleButton
	Private tbnStatTyp As ToggleButton
	Private tbnStkLvl As ToggleButton
	Private tbnStkTyp As ToggleButton
	Private tbnStock As ToggleButton
	Private tbnSystems As ToggleButton
	Private tbnTempStat As ToggleButton
	Private tbnGalAver As ToggleButton
	Private tbnFactions As ToggleButton
	
	Private Q As String
	Private Curs As Cursor
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("DBAdmin")
	
	Functions.SetColours(Activity)
	
End Sub

Sub Activity_Resume
	RefreshButtons
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnCommod_Click
	If tbnCommod.Checked = False Then
		Return
	End If
	StartActivity("CommodUpdate")
	
End Sub

Sub btnExit_Click
	EDTables.DatabaseSetup
	Activity.Finish
	
End Sub

Sub RefreshButtons

#Region Systems
	If SQLUtils.TableExists(Starter.SQLExec,"Systems") = True Then
		tbnSystems.Checked = True
		Q = "SELECT * FROM Systems"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnSystems.TextOn = "SYSTEMS (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnSystems.Checked = False
	End If
#End Region

#Region Stations
	If SQLUtils.TableExists(Starter.SQLExec,"Stations") = True Then
		tbnStations.Checked = True
		Q = "SELECT * FROM Stations"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnStations.TextOn = "STATIONS (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnStations.Checked = False
	End If
#End Region
	
#Region StockMarket
	If SQLUtils.TableExists(Starter.SQLExec,"StockMarket") = True Then
		tbnStock.Checked = True
		Q = "SELECT * FROM StockMarket"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnStock.TextOn = "STOCK MARKET (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnStock.Checked = False
	End If
#End Region

#Region Factions
	If SQLUtils.TableExists(Starter.SQLExec,"Factions") = True Then
		tbnFactions.Checked = True
		Q = "SELECT * FROM Factions"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnFactions.TextOn = "FACTIONS (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnFactions.Checked = False
	End If
#End Region

#Region CommodGroup
	If SQLUtils.TableExists(Starter.SQLExec,"CommodityGroups") = True Then
		tbnComGrp.Checked = True
		Q = "SELECT * FROM CommodityGroups"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnComGrp.TextOn = "COMMODITY GROUPS (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnComGrp.Checked = False
	End If
#End Region

#Region Commodities
	If SQLUtils.TableExists(Starter.SQLExec,"Commodities") = True Then
		tbnCommod.Checked = True
		Q = "SELECT * FROM Commodities"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnCommod.TextOn = "COMMODITIES (" & Curs.RowCount & ")"
		End If
		Curs.Close
		btnCommod.Enabled = True
	Else
		tbnCommod.Checked = False
		btnCommod.Enabled = False
	End If
#End Region

#Region Rares
	If SQLUtils.TableExists(Starter.SQLExec,"RareCommodities") = True Then
		tbnRares.Checked = True
		Q = "SELECT * FROM RareCommodities"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnRares.TextOn = "RARE COMMODITIES (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnRares.Checked = False
	End If
#End Region

#Region StockType
	If SQLUtils.TableExists(Starter.SQLExec,"StockTypes") = True Then
		tbnStkTyp.Checked = True
		Q = "SELECT * FROM StockTypes"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnStkTyp.TextOn = "STOCK TYPES (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnStkTyp.Checked = False
	End If
#End Region

#Region StockLevels
	If SQLUtils.TableExists(Starter.SQLExec,"StockLevels") = True Then
		tbnStkLvl.Checked = True
		Q = "SELECT * FROM StockLevels"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnStkLvl.TextOn = "STOCK LEVELS (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnStkLvl.Checked = False
	End If
#End Region

#Region Governments
	If SQLUtils.TableExists(Starter.SQLExec,"Governments") = True Then
		tbnGov.Checked = True
		Q = "SELECT * FROM Governments"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnGov.TextOn = "GOVERNMENTS (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnGov.Checked = False
	End If
#End Region

#Region Economies
	If SQLUtils.TableExists(Starter.SQLExec,"Economies") = True Then
		tbnEcon.Checked = True
		Q = "SELECT * FROM Economies"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnEcon.TextOn = "ECONOMIES (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnEcon.Checked = False
	End If
#End Region

#Region Allegiance
	If SQLUtils.TableExists(Starter.SQLExec,"Allegiance") = True Then
		tbnAlleg.Checked = True
		Q = "SELECT * FROM Allegiance"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnAlleg.TextOn = "ALLEGIANCE (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnAlleg.Checked = False
	End If
#End Region

#Region StationType
	If SQLUtils.TableExists(Starter.SQLExec,"StationTypes") = True Then
		tbnStatTyp.Checked = True
		Q = "SELECT * FROM StationTypes"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnStatTyp.TextOn = "STATION TYPES (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnStatTyp.Checked = False
	End If
#End Region

#Region Population
	If SQLUtils.TableExists(Starter.SQLExec,"Population") = True Then
		tbnPopu.Checked = True
		Q = "SELECT * FROM Population"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnPopu.TextOn = "POPULATION (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnPopu.Checked = False
	End If
#End Region

#Region Location
	If SQLUtils.TableExists(Starter.SQLExec,"Location") = True Then
		tbnCurrLoc.Checked = True
		Q = "SELECT * FROM Location"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnCurrLoc.TextOn = "LOCATION (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnCurrLoc.Checked = False
	End If
#End Region

#Region MaxLYTradeDistance
	If SQLUtils.TableExists(Starter.SQLExec,"MaxLYTradeDistance") = True Then
		tbnMaxLY.Checked = True
		Q = "SELECT * FROM MaxLYTradeDistance"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnMaxLY.TextOn = "MAX TRADE DISTANCE (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnMaxLY.Checked = False
	End If
#End Region

#Region TempStations
	If SQLUtils.TableExists(Starter.SQLExec,"TempStations") = True Then
		tbnTempStat.Checked = True
		Q = "SELECT * FROM TempStations"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount = 0 Then
			tbnTempStat.Checked = False
		Else
			tbnTempStat.Checked = True
		End If
		Curs.Close
	Else
		tbnTempStat.Checked =False
	End If
#End Region
	
#Region Galactic
	If SQLUtils.TableExists(Starter.SQLExec,"GalacticPrices") = True Then
		tbnGalAver.Checked = True
		Q = "SELECT * FROM GalacticPrices"
		Curs = Starter.SQLExec.ExecQuery(Q)
		If Curs.RowCount > 0 Then
			tbnGalAver.TextOn = "GALACTIC PRICES (" & Curs.RowCount & ")"
		End If
		Curs.Close
	Else
		tbnGalAver.Checked = False
	End If
#End Region
	
End Sub

#Region Delete Table Buttons

Sub tbnSystems_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Systems") = True Then
		'Delete Systems Table
		Q = "DROP TABLE Systems"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnStations_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Stations") = True Then
		'Delete Stations Table
		Q = "DROP TABLE Stations"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnFactions_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Factions") = True Then
		'Delete Factions Table
		Q = "DROP TABLE Factions"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnTempStat_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"TempStations") = True Then
		' Delete TempStations Table
		Q = "DROP TABLE TempStations"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnStock_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"StockMarket") = True Then
		' Delete StockMarket Data Table
		Q = "DROP TABLE StockMarket"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If

End Sub

Sub tbnComGrp_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"CommodityGroups") = True Then
		'Delete Commodity Groups Table
		Q = "DROP TABLE CommodityGroups"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If

End Sub

Sub tbnCommod_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Commodities") = True Then
		'Delete Commodities Table
		Q = "DROP TABLE Commodities"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnRares_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"RareCommodities") = True Then
		'Delete Rares Table
		Q = "DROP TABLE RareCommodities"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If

End Sub

Sub tbnStkTyp_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"StockTypes") = True Then
		'Delete Stock Type Table
		Q = "DROP TABLE StockTypes"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnStkLvl_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"StockLevels") = True Then
		'Delete Stock Levels Table
		Q = "DROP TABLE StockLevels"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If

End Sub

Sub tbnGov_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Governments") = True Then
		'Delete Governments Table
		Q = "DROP TABLE Governments"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnEcon_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Economies") = True Then
		'Delete Economy Table
		Q = "DROP TABLE Economies"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnAlleg_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Allegiance") = True Then
		'Delete Allegiance Table
		Q = "DROP TABLE Allegiance"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnStatTyp_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"StationTypes") = True Then
		'Delete Station Types Table
		Q = "DROP TABLE StationTypes"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnPopu_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Population") = True Then
		' Delete Population Table
		Q = "DROP TABLE Population"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnCurrLoc_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"Location") = True Then
		' Delete Location Table
		Q = "DROP TABLE Location"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnMaxLY_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"MaxLYTradeDistance") = True Then
		' Delete Table
		Q = "DROP TABLE MaxLYTradeDistance"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

Sub tbnGalAver_CheckedChange(Checked As Boolean)
	If Checked = True Then
		Return
	End If
	
	If SQLUtils.TableExists(Starter.SQLExec,"GalacticPrices") = True Then
		' Delete Table
		Q = "DROP TABLE GalacticPrices"
		Starter.SQLExec.ExecNonQuery(Q)
		RefreshButtons
	End If
	
End Sub

#End Region

