export class UserSave {
  name?: string;
  firstSurname?: string;
  secondSurname?: string;
  email?: string;
  phone?: string;
  user?: string;
  identification?: string;
  address?: string;
  postalCode?: string;
  municipality?: string;
  province?: string;
  role?: string;

  constructor(name: string, firstSurname: string, secondSurname: string, email: string, phone: string, user: string, identification: string, address: string, postalCode: string, municipality: string, province: string, role: string) {
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
  }
}
