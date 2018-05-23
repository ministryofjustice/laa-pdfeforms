import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceeditComponent } from './attendanceedit.component';

describe('AttendanceeditComponent', () => {
  let component: AttendanceeditComponent;
  let fixture: ComponentFixture<AttendanceeditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendanceeditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendanceeditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
