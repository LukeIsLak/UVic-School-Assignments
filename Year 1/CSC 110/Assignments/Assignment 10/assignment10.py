import doctest
from pet import Pet
from date import Date

# represents a pet as (name, species)
PetNameSpecies = tuple[str,str]

# columns of values within input file row and within PetNameSpecies tuple
NAME    = 0
SPECIES = 1
MONTH   = 2
DAY     = 3
YEAR    = 4

def read_file(filename: str) -> list[Pet]:
    ''' returns a list of Pets populated with data from filename
    
    Preconditions: filename exists.
    If filename is not empty, each row has a single Pet's information
    separated by commas with expected values at columns:
    NAME, SPECIES, MONTH, DAY and YEAR.

    >>> read_file('empty.csv')
    []
    >>> read_file('pet_data.csv')
    [Pet('Rover', 'Dog', Date(12, 31, 2010)), \
Pet('Red', 'Cat', Date(9, 15, 2016)), \
Pet('Chewie', 'Hamster', Date(1, 23, 2009)), \
Pet('Sam', 'Budgie', Date(3, 29, 1990)), \
Pet('Ollie', 'Dog', Date(2, 8, 2009)), \
Pet('Scout', 'Dog', Date(9, 15, 2016))]
    '''
    # TODO: complete this function
    file = open(filename, 'r', encoding="utf8")
    
    line = file.readline()
    list_pet = []
    while line != '':
        data = tuple(line[:len(line)].split(','))
        
        name = data[NAME]
        species = data[SPECIES]
        date = Date(int(data[MONTH]), int(data[DAY]), int(data[YEAR]))
        
        curr_pet = Pet(name, species, date)
        list_pet.append(curr_pet)
        
        line = file.readline()
    
    file.close()
    return list_pet

def find_pet(lopets: list[Pet], name: str) -> int:
    ''' returns the position of pet with given name in lopets
    Returns -1 if name not found
    Returns the position of the first found if there >1 Pet with the given name
    
    Precondition: name must match case exactly
    ie. 'rover' does not match 'Rover'

    >>> find_pet([], 'Fred')
    -1
    >>> find_pet([Pet('Rover', 'Dog', Date(12, 22, 2020)), \
Pet('Red', 'Cat', Date(1, 2, 2019))], 'Red')
    1
    >>> find_pet([Pet('Rover', 'Dog', Date(12, 22, 2020)), \
Pet('Red', 'Cat', Date(1, 2, 2019))], 'Bowser')
    -1
    >>> find_pet([Pet('Red', 'Dog', Date(12, 22, 2020)), \
Pet('Red', 'Cat', Date(1, 2, 2019))], 'Red')
    0
    '''
    count = 0
    found_pet = False
    for pet in lopets:
        if pet.get_name() == name:
            found_pet = True
            break
        count += 1
    return count if found_pet else -1

def get_all_of_species(lopets: list[Pet], species: str) -> list[Pet]:
    ''' returns a list of all pets of the given species.
    Result list is not necessarily unique, if >1 Pet in lopets has the same name.
    
    Precondition: species must match case exactly
    ie. 'dog' does not match 'Dog'
    
    >>> pets = [Pet('Rover', 'Dog', Date(12, 31, 2010)), \
Pet('Red', 'Cat', Date(9, 15, 2016)), \
Pet('Chewie', 'Hamster', Date(1, 23, 2009)), \
Pet('Sam', 'Budgie', Date(3, 29, 1990)), \
Pet('Ollie', 'Dog', Date(2, 8, 2009)), \
Pet('Scout', 'Dog', Date(9, 15, 2016))]

    >>> get_all_of_species([], 'Dog')
    []
    >>> get_all_of_species(pets, 'Dog')
    [Pet('Rover', 'Dog', Date(12, 31, 2010)), \
Pet('Ollie', 'Dog', Date(2, 8, 2009)), \
Pet('Scout', 'Dog', Date(9, 15, 2016))]
    >>> get_all_of_species(pets, 'Tiger')
    []
    >>> get_all_of_species(pets, 'Hamster')
    [Pet('Chewie', 'Hamster', Date(1, 23, 2009))]
    '''
    all_spec = []
    for pet in lopets:
        if pet.get_species() == species:
            all_spec.append(pet)
    
    return all_spec


def get_latest_birthdate(lopets: list[Pet]) -> Date:
    ''' returns the latest Date of all birthdates of Pet instances in lopets
    Precondition: lopets is not empty
    >>> pets = [Pet('Rover', 'Dog', Date(12, 31, 2010)), \
Pet('Red', 'Cat', Date(9, 15, 2016)), \
Pet('Chewie', 'Hamster', Date(1, 23, 2009)), \
Pet('Sam', 'Budgie', Date(3, 29, 1990)), \
Pet('Ollie', 'Dog', Date(2, 8, 2009)), \
Pet('Scout', 'Dog', Date(9, 15, 2016))]

    >>> get_latest_birthdate([Pet('Rover', 'Dog', Date(12, 31, 2010))])
    Date(12, 31, 2010)
    >>> get_latest_birthdate(pets)
    Date(9, 15, 2016)
    '''
    latest_birthday = lopets[0].get_birthdate()
    for index in range(len(lopets) - 1):
        if lopets[index+1].get_birthdate().is_after(latest_birthday):
            latest_birthday = lopets[index+1].get_birthdate()
    
    return latest_birthday


def get_youngest_pets(lopets: list[Pet]) -> list[PetNameSpecies]:
    ''' returns a list of PetNameSpecies of all the youngest pets in lopets
    >>> pets = [Pet('Rover', 'Dog', Date(12, 31, 2010)), \
Pet('Red', 'Cat', Date(9, 15, 2016)), \
Pet('Chewie', 'Hamster', Date(1, 23, 2009)), \
Pet('Sam', 'Budgie', Date(3, 29, 1990)), \
Pet('Ollie', 'Dog', Date(2, 8, 2009)), \
Pet('Scout', 'Dog', Date(9, 15, 2016))]
    >>> get_youngest_pets([])
    []
    >>> get_youngest_pets(pets)
    [('Red', 'Cat'), ('Scout', 'Dog')]
    '''
    # TODO: complete this function
    youngest_pets = []
    youngest_bd = 'filler_val'
    for pet in lopets:
        if str(youngest_bd) == 'filler_val':
            youngest_pets.append((pet.get_name(), pet.get_species()))
            youngest_bd = pet.get_birthdate()
        else:
            birthdate = pet.get_birthdate()
            if birthdate.is_after(youngest_bd) or youngest_bd == birthdate:
                pet_vals = (pet.get_name(), pet.get_species())
                if birthdate == youngest_bd:
                    youngest_pets.append(pet_vals)
                else:
                    youngest_pets = [pet_vals]
                    youngest_bd = birthdate
    
    return youngest_pets
        
        
