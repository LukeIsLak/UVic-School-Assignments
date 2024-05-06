import doctest
def print_qualification_status(racer_time: float, qualify_time:float) -> None:
    '''
    prints whether or not a time qualifies given the qualify time
    >>> print_qualification_status(50, 60)
    You qualified at 10.00 seconds below qualifying time
    >>> print_qualification_status(67, 60)
    You missed qualifying by 7.00 seconds
    >>> print_qualification_status(32.3, 60)
    You qualified at 27.70 seconds below qualifying time
    >>> print_qualification_status(68.7, 60)
    You missed qualifying by 8.70 seconds
    '''
    if racer_time < qualify_time:
        spare_time = qualify_time - racer_time
        print(f'You qualified at {spare_time:.2f} seconds below qualifying time')
    else:
        over_time = racer_time - qualify_time
        print(f'You missed qualifying by {over_time:.2f} seconds')
        
def print_median(n1: float, n2: float, n3:float) -> None:
    '''
    prints the middle number in a set of three numbers
    >>> print_median(0, 3, 1)
    1
    >>> print_median(5, 6, 1)
    5
    >>> print_median(0, -3, 18)
    0
    >>> print_median(-27, 3, -81)
    -27
    >>> print_median(1, -3, -2)
    -2
    >>> print_median(0, 0, 0)
    0
    >>> print_median(0, 0, 1)
    0
    '''
    if n1 >= n2 and n1 <= n3 or n1 >= n3 and n1 <= n2:
        print(n1)
    elif n2 >= n1 and n2 <= n3 or n2 >= n3 and n2 <= n1:
        print(n2)
    elif n3 >= n1 and n3 <= n2 or n3 >= n3 and n3 <= n1:
        print(n3)        

def print_triangle_type(side_1:float, side_2:float, side_3:float) -> None:
    '''
    prints the type of triangle based on the given three side lengths
    >>> print_triangle_type(3, 3, 3)
    equilateral
    >>> print_triangle_type(2, 2, 5)
    sosceles
    >>> print_triangle_type(3, 4, 5)
    scalene
    '''
    if side_1 == side_2 and side_1 == side_3:
        print('equilateral')
    elif side_1 == side_2 or side_1 == side_3 or side_2 == side_3:
        print('isosceles')
    else:
        print('scalene')
        
def is_multiple_of(n1, n2) -> None:
    '''
    prints if n1 is a multiple of n2
    >>> is_multiple_of(0, 2)
    0 is not a multiple of 2
    >>> is_multiple_of(2, 0)
    2 is not a multiple of 0
    >>> is_multiple_of(8, 4)
    8 is a multiple of 4
    >>> is_multiple_of(81, 13)
    81 is not a multiple of 13
    '''
    if n1 == 0 or n2 == 0:
        print(f'{n1} is not a multiple of {n2}')
    else:   
        remainder = n1%n2
        if remainder == 0:
            print(f'{n1} is a multiple of {n2}')
        else:
            print(f'{n1} is not a multiple of {n2}')
            
def print_roadside_penalty(blood_alcohol_lv: int, incident_num: int, breath_sample: bool) -> None:
    '''
    prints the penalty for blood alcohol levels for driving. Assuming that all number are positive and whole
    >>> print_roadside_penalty(10, 0, False)
    Driving Prohibition Length: 90 days
    Vehicle Impoundment Length: 30 days
    Penalties and fees: $1430
    >>> print_roadside_penalty(49, 2, True)
    no penalty
    >>> print_roadside_penalty(50, 1, True)
    Driving Prohibition Length: 3 days
    Vehicle Impoundment Length: 3 days
    Penalties and fees: $600
    >>> print_roadside_penalty(60, 2, True)
    Driving Prohibition Length: 7 days
    Vehicle Impoundment Length: 7 days
    Penalties and fees: $780
    >>> print_roadside_penalty(79, 3, True)
    Driving Prohibition Length: 30 days
    Vehicle Impoundment Length: 30 days
    Penalties and fees: $1330
    >>> print_roadside_penalty(85, 5, True)
    Driving Prohibition Length: 90 days
    Vehicle Impoundment Length: 30 days
    Penalties and fees: $1430
    '''
    #warning level
    if blood_alcohol_lv >= 50 and blood_alcohol_lv < 80 and breath_sample:
        #first incident
        if incident_num == 1:
            print(f'Driving Prohibition Length: 3 days')
            print(f'Vehicle Impoundment Length: 3 days')
            print(f'Penalties and fees: $600')
        #second incident
        elif incident_num == 2:
            print(f'Driving Prohibition Length: 7 days')
            print(f'Vehicle Impoundment Length: 7 days')
            print(f'Penalties and fees: $780')
        #third incident
        elif incident_num >= 3:
            print(f'Driving Prohibition Length: 30 days')
            print(f'Vehicle Impoundment Length: 30 days')
            print(f'Penalties and fees: $1330')            
    elif blood_alcohol_lv >= 80 and breath_sample or not breath_sample:
        print(f'Driving Prohibition Length: 90 days')
        print(f'Vehicle Impoundment Length: 30 days')
        print(f'Penalties and fees: $1430') 
    else:
        print('no penalty')
    