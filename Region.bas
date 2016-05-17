Type=Class
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@

Sub Class_Globals
	Private minX, maxX, minY, maxY, minZ, maxZ As Double
	Public systemcounter As Int
	Private centerX, centerY, centerZ As Double
	Private regionSize As Int
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize
	minX = 0
	maxX = 0
	minY = 0
	maxY = 0
	minZ = 0
	maxZ = 0
	centerX = 0
	centerY = 0
	centerZ = 0
	systemcounter = 0
	regionSize = 1
End Sub

Public Sub getminX As Double
	Return minX
End Sub

Public Sub getminY As Double
	Return minY
End Sub

Public Sub getminZ As Double
	Return minZ
End Sub

Public Sub getmaxX As Double
	Return maxX
End Sub

Public Sub getmaxY As Double
	Return maxY
End Sub

Public Sub getmaxZ As Double
	Return maxZ
End Sub

Private Sub setminX(XCenter As Double)
	minX = Floor(XCenter * 32 - regionSize) / 32
End Sub

Private Sub setmaxX(XCenter As Double)
	maxX = Ceil(XCenter * 32 + regionSize) /32
End Sub

Private Sub setminY(YCenter As Double)
	minY = Floor(YCenter * 32 - regionSize) / 32
End Sub

Private Sub setmaxY(YCenter As Double)
	maxY = Ceil(YCenter * 32 + regionSize) / 32
End Sub

Private Sub setminZ(ZCenter As Double)
	minZ = Floor(ZCenter * 32 - regionSize) / 32
End Sub

Private Sub setmaxZ(ZCenter As Double)
	maxZ = Ceil(ZCenter * 32 + regionSize) / 32
End Sub

Public Sub setAll(coord As Coordinate)
	setminX(coord.X)
	setmaxX(coord.X)
	setminY(coord.Y)
	setmaxY(coord.Y)
	setminZ(coord.Z)
	setmaxZ(coord.Z)
End Sub

Public Sub getCenter As Coordinate
	Dim center As Coordinate
	centerX = ((maxX - minX) / 2) + minX
	centerY = ((maxY - minY) / 2) + minY
	centerZ = ((maxZ - minZ) / 2) + minZ
	center.Initialize(centerX, centerY, centerZ)
	Return center
End Sub

Public Sub contained(coord As Coordinate) As Boolean
	Return (coord.X >= minX And coord.X <= maxX And coord.Y >= minY And coord.Y <= maxY And coord.Z >= minZ And coord.Z <= maxZ)
End Sub

Public Sub getSysCount As Int
	Return systemcounter
End Sub

Public Sub setSysCount(count As Int)
	systemcounter = count
End Sub

Public Sub volume As Double
	Return (32768 * (maxX - minX + 1 / 32) * (maxY - minY + 1 / 32) * (maxZ - minZ + 1 / 32))
End Sub

Public Sub union(r As Region) As Region
	Dim u As Region
	u.Initialize
	
	u.minX = Min(minX, r.minX)
	u.minY = Min(minY, r.minY)
	u.minZ = Min(minZ, r.minZ)
	u.maxX = Max(maxX, r.maxX)
	u.maxY = Max(maxY, r.maxY)
	u.maxZ = Max(maxZ, r.maxZ)
	
	Return u
End Sub