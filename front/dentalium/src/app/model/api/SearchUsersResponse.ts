export class SearchUsersResponse {

  users: User[]


  constructor(users: User[]) {
    this.users = users;
  }
}


export class User {
  id: number;
  name: string;
  firstSurname: string;
  secondSurname: string;
  email: string;
  phone: string;
  user: string;
  identification: string;
  address: string;
  postalCode: string;
  municipality: string;
  province: string;
  role: string;
  active: boolean;
  userReference?: UserReference;

  constructor(id: number, name: string, firstSurname: string, secondSurname: string, email: string, phone: string, user: string, identification: string, address: string, postalCode: string, municipality: string, province: string, role: string, active: boolean, userReference: UserReference) {
    this.id = id;
    this.name = name;
    this.firstSurname = firstSurname;
    this.secondSurname = secondSurname;
    this.email = email;
    this.phone = phone;
    this.user = user;
    this.identification = identification;
    this.address = address;
    this.postalCode = postalCode;
    this.municipality = municipality;
    this.province = province;
    this.role = role;
    this.active = active;
    this.userReference = userReference;
  }
}

export class UserReference {
  id?: string;
  name?: string;

  constructor (id: string, name: string) {
    this.id = id;
    this.name = name;
  }
}
