class PostData{

    /**
     * Create a new instance of object to be posted for registration.
     * 
     * @param {String} Username Username
     * @param {String} Firstname Firstname
     * @param {String} Lastname Lastname
     * @param {String} DailCode Country Dail Code
     * @param {String} PhoneNumber Phone Number
     * @param {String} Email Email
     * @param {int} AgeGroup Age Group Identifeir
     * @param {int} Team Team Identifeir
     * @param {int[]} Positions Postion Identifeirs 
     * @param {String} Address Address
     * @param {String} PinCode Pinocde
     * @param {String} Country Country Identifeir
     * @param {String} State State Identifeir
     * @param {String} City City Identifeir
     */
     constructor(Username, Firstname, Lastname, DailCode, PhoneNumber, Email, AgeGroup, 
        Team, Positions, Address, PinCode, Country, State, City){
        this.Username =Username;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.DailCode = DailCode;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.AgeGroup =AgeGroup;
        this.Team =Team;
        this.Positions = Positions;
        this.Address = Address;
        this.PinCode = PinCode;
        this.Country = Country;
        this.State = State;
        this.City = City;
    }


}