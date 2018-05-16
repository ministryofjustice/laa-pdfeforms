import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendancenoteindexComponent } from './attendancenoteindex.component';

describe('AttendancenoteindexComponent', () => {
  let component: AttendancenoteindexComponent;
  let fixture: ComponentFixture<AttendancenoteindexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendancenoteindexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendancenoteindexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
