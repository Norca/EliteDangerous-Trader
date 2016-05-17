Type=Class
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@

Sub Class_Globals
	Private coordx, coordy, coordz As Double
	Private distance As Double
	Private totalsqr As Double
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize(x As Double, y As Double, z As Double)
	coordx = x
	coordy = y
	coordz = z
	distance = 0
	totalsqr = 0
End Sub

Public Sub getX As Double
	Return coordx
End Sub

Public Sub getY As Double
	Return coordy
End Sub

Public Sub getZ As Double
	Return coordz
End Sub

Public Sub setX(x As Double)
	coordx = x
End Sub

Public Sub setY(y As Double)
	coordy = y
End Sub

Public Sub setZ(z As Double)
	coordz = z
End Sub

Public Sub getDist As Double
	Return distance
End Sub

Public Sub setDist(dist As Double)
	distance = dist
End Sub

Public Sub getTotSqrErr As Double
	Return totalsqr
End Sub

Public Sub setTotSqrErr(tse As Double)
	totalsqr = tse
End Sub