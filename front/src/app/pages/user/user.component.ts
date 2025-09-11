import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/interfaces/user';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserComponent implements OnInit {
  public user: User | undefined;
  public profileForm: FormGroup;
  public updateSuccess = false;

   constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) {
    this.profileForm = this.fb.group({
      username: [''], 
      email: ['', [Validators.email]],
      password: ['']
    });
  }

  ngOnInit(): void {
    this.fetchUser();
  }

  private fetchUser(): void {
    this.userService.get().subscribe(user => {
      this.user = user;
      // This pre-fills the form with the user's current data
      this.profileForm.patchValue(user);
    });
  }

 public submit(): void {
    if (this.profileForm.valid) {
      const updatedUser = this.profileForm.value;
      
      if (!updatedUser.password) {
        delete updatedUser.password;
      }
      
      this.userService.update(updatedUser).subscribe(() => {
        console.log('Profile updated successfully');
        // Re-fetch user to ensure data is fresh, especially if username changed
        this.fetchUser(); 
        this.updateSuccess = true;
        setTimeout(()=> this.updateSuccess= false, 3000);
      });
    }
  }

  public unsubscribe(themeId: number): void {
    this.userService.unsubscribe(themeId).subscribe(() => {
      this.fetchUser();
    });
  }

 
}
