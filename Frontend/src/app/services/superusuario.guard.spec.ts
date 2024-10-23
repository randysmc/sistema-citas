import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { superusuarioGuard } from './superusuario.guard';

describe('superusuarioGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => superusuarioGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
