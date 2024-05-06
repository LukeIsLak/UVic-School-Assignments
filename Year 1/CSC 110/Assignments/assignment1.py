import doctest
import math

def print_bird():
    '''
    prints a picture of a bird coming out of it's shell. Used in the print_logo function
    >>> print_bird()
      ,''.
     (9/\9)
    |\}\/{ /| ,     ,
    \ `^./^ / \`/\/`/
     `.___.'   `._,'

    '''
    print('  ,\'\'.\n (9/\9)\n|\}\/{ /| ,     ,\n\ `^./^ / \`/\/`/\n `.___.\'   `._,\'')

def print_cat():
    '''
    prints a picture of a cat sleeping. Used in the print_logo function
    >>> print_cat()
          |\      _,,,---,,_
    ZZZzz /,`.-'`'    -.  ;-;;,_
         |,4-  ) )-,_. ,\ (  `'-'
        '---''(_/--'  `-'\_)
    '''
    print("      |\      _,,,---,,_\nZZZzz /,`.-\'`\'    -.  ;-;;,_\n     |,4-  ) )-,_. ,\ (  `\'-\'\n    \'---\'\'(_/--\'  `-\'\_)")

def print_spacer():
    '''
    prints a spacer for the print_logo() function
    >>> print_spacer()
    /~~~~~~~~\ 
    '''
    print('/~~~~~~~~\ ')
    

def print_logo():
    '''
    Prints outs a small scroll of images to create a logo. Images are a bird and a cat reference at help(print_bird) help(print_cat) or each respective function
    >>> print_logo()
    /~~~~~~~~\ 
      ,''.
     (9/\9)
    |\}\/{ /| ,     ,
    \ `^./^ / \`/\/`/
     `.___.'   `._,'
    /~~~~~~~~\ 
          |\      _,,,---,,_
    ZZZzz /,`.-'`'    -.  ;-;;,_
         |,4-  ) )-,_. ,\ (  `'-'
        '---''(_/--'  `-'\_)
    /~~~~~~~~\ 
      ,''.
     (9/\9)
    |\}\/{ /| ,     ,
    \ `^./^ / \`/\/`/
     `.___.'   `._,'
    /~~~~~~~~\ 
          |\      _,,,---,,_
    ZZZzz /,`.-'`'    -.  ;-;;,_
         |,4-  ) )-,_. ,\ (  `'-'
        '---''(_/--'  `-'\_)
    /~~~~~~~~\ 
    '''
    print_spacer()
    print_bird()
    print_spacer()
    print_cat()
    print_spacer()
    print_bird()
    print_spacer()
    print_cat()
    print_spacer()
    


def calculate_surface_area(height: float, diameter: float):
    '''
    prints the surface area of a cylinder
    >>> calculate_surface_area(1.2, 3.5)
    32.4
    >>> calculate_surface_area(10, 5)
    196.3
    '''
    radius = diameter / 2
    circumference = 2 * math.pi * radius
    wall_area = circumference * height
    circle_area = math.pi * radius**2
    
    total_surface_area = wall_area + circle_area*2
    print(f'{total_surface_area:.1f}')