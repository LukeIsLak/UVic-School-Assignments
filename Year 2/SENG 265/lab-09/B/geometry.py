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
