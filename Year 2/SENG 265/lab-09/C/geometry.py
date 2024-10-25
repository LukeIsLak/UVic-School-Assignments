import math

class Point:

    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def __repr__(self):
        return "Point(%r, %r)" % (self.x, self.y)

    def delta_x(self, dx=0):
        return Point(self.x+dx, self.y)
    def delta_y(self, dy=0):
        return Point(self.x, self.y+dy)
    def translate (self, dx=0, dy=0):
        return Point(self.x+dx, self.y+dy)


class Circle:
	def __init__(self, center, radius=0):
		self.center = center
		self.radius = radius
	def __repr__(self):
		return "Circle(%r, %r)" % (self.center, self.radius)
	def area(self):
		return math.pi * self.radius * self.radius
	def perimeter(self):
		return 2 * math.pi * self.radius
	def translate (self, dx=0, dy=0):
		return Circle(self.center.translate(dx, dy), self.radius)