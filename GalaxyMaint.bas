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
	Private btnExit As Button

	Private thGalaxy As TabHost
	Private TabManager As TabHostExtras

End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("GalaxyMaint")
	
	'Load Tab Panel Pages
	Dim bmp1, bmp2 As Bitmap
	bmp1 = LoadBitmap(File.DirAssets, "ic.png")
	bmp2 = LoadBitmap(File.DirAssets, "ic_selected.png")
	
	thGalaxy.AddTabWithIcon ("Systems", bmp1, bmp2, "SystemTab") 'load the layout file of each page
	thGalaxy.AddTab("Stations", "StationTab")
	thGalaxy.AddTab("Factions", "FactionTab")
	thGalaxy.AddTab("Stock", "StockTab")

	TabManager.setTabHeight(thGalaxy,35)
	TabManager.setTabTextColor(thGalaxy, Starter.TextColour)

	Functions.SetColours(Activity)

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#Region Buttons

Sub btnExit_Click
	Activity.Finish
End Sub


#End Region

Sub thGalaxy_TabChanged
	Functions.SetColours(Activity)
End Sub