import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendancenotecreateComponent } from './attendancenotecreate.component';

describe('AttendancenotecreateComponent', () => {
  let component: AttendancenotecreateComponent;
  let fixture: ComponentFixture<AttendancenotecreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendancenotecreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendancenotecreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
