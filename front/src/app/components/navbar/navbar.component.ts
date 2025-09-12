import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/services/session/session.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  public isMenuOpen = false;
  public toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  constructor(private sessionService: SessionService) {}

  logout() {
    this.sessionService.logOut();
  }

  ngOnInit(): void {}
}
