# StackFragment UI library

The library can you used to provide a stacked UI format for collecting information or choosing options related to a same topic.

## Usage
- Clone the project
- Add compile **StackFragmentSampleApp/StackFrag/app** module in your application
- Creat your fragments extending CustomStackFragment which needs to be added to the stack.
- Create a StackFragmentHolder instance and add your fragments to it. 
- StackFragmentHolder is also a fragment. Simply populate it in your activity.
- Use Android suggested Jetpack Navigation Component for populating StackFragment.
- Make sure to override onBackPressed listener in your activity and update the event to StackFragement for getting expand and collapse callbacks.


## Code breakdown

### Adding stack fragments

```
val stackFragment = navHostFragment!!.childFragmentManager.fragments.first() as StackFragmentsHolder
stackFragment.addFragment(Frag1Bottom())
stackFragment.addFragment(Frag2Bottom())
stackFragment.addFragment(Frag3Bottom())
```

### Provide onBackPressed event to get expand and collapsed callback
```
override fun onBackPressed() {
        super.onBackPressed()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
        val stackFragment = navHostFragment!!.childFragmentManager.fragments[0] as StackFragmentsHolder
        stackFragment.onBackPressed()
    }
```

### Mention the peek height value for the previous fragment to be visible enough
The collapse state of each fragment might differ based on the UI, hence the library requires the peek height value from the fragment that extends it.
```
override fun getPeekHeight(): Int {
        return requireView().measuredHeight - (2*resources.getDimension(R.dimen.collapse_view_height)).toInt()
    }
```

#### Note: The library works with minimum 2 and maximum 4 fragments
